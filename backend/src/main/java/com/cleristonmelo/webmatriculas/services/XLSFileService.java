package com.cleristonmelo.webmatriculas.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cleristonmelo.webmatriculas.dtos.ParentDTO;
import com.cleristonmelo.webmatriculas.dtos.PhoneDTO;
import com.cleristonmelo.webmatriculas.dtos.StudentDTO;
import com.cleristonmelo.webmatriculas.entities.Address;
import com.cleristonmelo.webmatriculas.entities.City;
import com.cleristonmelo.webmatriculas.entities.NationalId;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.Phase;
import com.cleristonmelo.webmatriculas.entities.Phone;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.State;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.enums.Gender;
import com.cleristonmelo.webmatriculas.entities.enums.Nationality;
import com.cleristonmelo.webmatriculas.entities.enums.Period;
import com.cleristonmelo.webmatriculas.entities.enums.Race;
import com.cleristonmelo.webmatriculas.repositories.CityRepository;
import com.cleristonmelo.webmatriculas.repositories.NationalIdRepository;
import com.cleristonmelo.webmatriculas.repositories.PhaseRepository;
import com.cleristonmelo.webmatriculas.repositories.PhoneRepository;
import com.cleristonmelo.webmatriculas.repositories.SchoolClassRepository;
import com.cleristonmelo.webmatriculas.repositories.StateRepository;
import com.cleristonmelo.webmatriculas.repositories.StudentRepository;

@Service
public class XLSFileService {
	private static Logger LOG = LoggerFactory.getLogger(XLSFileService.class);

	private static final String NINTH_DIGIT = "9";
	private static final String BR_MA_AREA_CODE = "98";
	private static final String STANDARD_COUNTRY = "BR";

	@Autowired
	private PhaseRepository phaseRepository;

	@Autowired
	private SchoolClassRepository schoolClassRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private NationalIdRepository nationalIdRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private StudentService studentService;

	public void getDataFromXLSFile(MultipartFile file) {
		try {
			LOG.info("File received");

			InputStream fileIS = file.getInputStream();

			Workbook workbook = new XSSFWorkbook(fileIS);
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			Set<StudentDTO> students = new HashSet<>();
			Set<Long> nationalIds = new HashSet<>();
			Set<Long> phoneNumbers = new HashSet<>();

			nationalIdRepository.findAll().stream().map(nat -> nationalIds.add(nat.getNumber()));
			phoneRepository.findAll().stream().map(phn -> phoneNumbers.add(phn.getNumber()));

			for (int i = 0; i < rows; i++) {
				Row row = sheet.getRow(i);

				Phase phase = new Phase();
				SchoolClass schoolClass = new SchoolClass();
				StudentDTO student = new StudentDTO();
				State birthState = new State();
				State nationalIdState = new State();
				City birthPlace = new City();
				City nationalIdCity = new City();
				NationalId nationalId = new NationalId();
				Address address = new Address();

				for (Cell cell : row) {
					if (cell.getRowIndex() > 1 && cell.getColumnIndex() > 3) {

						switch (cell.getColumnIndex()) {

						case 4:
							String description = cell.getRichStringCellValue().getString();
							if (description != null) {
								Phase phs = phaseRepository.findByDescriptionLike(description);
								if (phs != null) {
									phase = phs;
								} else {
									phase.setDescription(description.trim());
									phaseRepository.save(phase);
								}
							}
							break;

						case 5:
							String name = cell.getRichStringCellValue().getString();
							if (name != null) {
								SchoolClass schc = schoolClassRepository.findByName(name);
								if (schc != null) {
									schoolClass = schc;
								} else {
									schoolClass.setName(name.trim());
									schoolClass.setPhase(phase);
								}
							}
							break;

						case 6:
							if (schoolClass.getPeriod() == null) {
								String period = cell.getRichStringCellValue().getString();
								if (period != null) {
									switch (period.trim()) {
									case "MATUTINO":
										schoolClass.setPeriod(Period.MORNING);
										break;
									case "VESPERTINO":
										schoolClass.setPeriod(Period.EVENING);
										break;
									case "NOTURNO":
										schoolClass.setPeriod(Period.NIGHT);
										break;
									default:
										break;
									}
									SchoolClass savedSC = schoolClassRepository.save(schoolClass);
									student.setSchoolClass(savedSC);
								}
							} else {
								student.setSchoolClass(schoolClass);
							}
							break;

						case 7:
							String incomingEnrollment = cell.getRichStringCellValue().toString();
							if (incomingEnrollment != null) {
								Long enrollment = null;
								if (incomingEnrollment.matches("\\d*")) {
									enrollment = Long.parseLong(incomingEnrollment.trim());
								}
								Optional<Student> std = studentRepository.findById(enrollment);
								if (std.isEmpty()) {
									student.setEnrollment(enrollment);
								} else {
									student = new StudentDTO(std.get());
								}
							}
							break;

						case 9:
							if (student.getName() == null || student.getLastName() == null) {
								String fullName = cell.getRichStringCellValue().toString();
								if (fullName != null) {
									String[] splitedName = fullName.split(" ");
									student.setName(splitedName[0].trim());

									StringBuilder lastName = new StringBuilder();
									for (int j = 1; j < splitedName.length; j++) {
										lastName.append(splitedName[j]);
										lastName.append(" ");
									}
									student.setLastName(lastName.toString().trim());
								}
							}
							break;

						case 10:
							if (student.getSocialId() == null) {
								String incomingSocialId = cell.getRichStringCellValue().toString();
								if (incomingSocialId != null) {
									String socialIdAsString = incomingSocialId.substring(1);
									Long socialId = null;
									if (socialIdAsString.matches("\\d{11}")) {
										socialId = Long.parseLong(socialIdAsString);
									}

									boolean result = checkSocialIdUniqueness(socialId);

									if (result) {
										socialId = null;
									}
									student.setSocialId(socialId);
								}
							}
							break;

						case 11:
							if (student.getBirthDate() == null) {
								String incomingBirthDate = cell.getRichStringCellValue().toString();
								if (incomingBirthDate != null) {
									String[] iBDSplited = incomingBirthDate.split("/");
									LocalDate birthDate = null;
									if (iBDSplited[0].matches("\\d{2}") && iBDSplited[1].matches("\\d{2}")
											&& iBDSplited[2].matches("\\d{2,4}")) {
										birthDate = LocalDate.of(Integer.parseInt(iBDSplited[2]),
												Integer.parseInt(iBDSplited[1]), Integer.parseInt(iBDSplited[0]));
									}
									student.setBirthDate(birthDate);
								}
							}
							break;

						case 12:
							if (student.getGender() == null) {
								String gender = cell.getRichStringCellValue().getString();
								if (gender != null) {
									switch (gender.trim()) {
									case "MASCULINO":
										student.setGender(Gender.MALE);
										break;
									case "FEMININO":
										student.setGender(Gender.FEMALE);
										break;
									default:
										student.setGender(Gender.NON_BINARY);
										break;
									}
								}
							}
							break;

						case 14:
							String parent1FullName = cell.getRichStringCellValue().toString();
							if (parent1FullName != null) {
								Parent parent1 = new Parent();
								String[] parent1SplitedName = parent1FullName.split(" ");
								parent1.setName(parent1SplitedName[0]);

								StringBuilder parent1LastName = new StringBuilder();
								for (int j = 1; j < parent1SplitedName.length; j++) {
									parent1LastName.append(parent1SplitedName[j]);
									parent1LastName.append(" ");
								}
								parent1.setLastName(parent1LastName.toString().trim());
								ParentDTO prtDto1 = new ParentDTO(parent1);
								student.getParents().add(prtDto1);
							}
							break;

						case 15:
							String parent2FullName = cell.getRichStringCellValue().toString();
							if (parent2FullName != null) {
								Parent parent2 = new Parent();
								String[] parent2SplitedName = parent2FullName.split(" ");
								parent2.setName(parent2SplitedName[0]);

								StringBuilder parent2LastName = new StringBuilder();
								for (int j = 1; j < parent2SplitedName.length; j++) {
									parent2LastName.append(parent2SplitedName[j]);
									parent2LastName.append(" ");
								}
								parent2.setLastName(parent2LastName.toString().trim());
								ParentDTO prtDto2 = new ParentDTO(parent2);
								student.getParents().add(prtDto2);
							}
							break;

						case 16:
							String incomingPhone1 = cell.getRichStringCellValue().toString();
							if (incomingPhone1 != null) {
								Phone phone1 = new Phone();
								String[] phone1Splited = incomingPhone1.split("-");
								StringBuilder phone1Concat = new StringBuilder();
								for (String str : phone1Splited) {
									phone1Concat.append(str);
								}
								String phn1 = phone1Concat.toString();
								Long phoneNr = null;

								if (phn1.matches("\\d{8,11}")) {
									Character c1 = phn1.charAt(0);
									if (c1.toString().compareTo("3") == 0) {
										phn1 = BR_MA_AREA_CODE + phn1;
									} else if (phn1.length() < 9) {
										phn1 = BR_MA_AREA_CODE + NINTH_DIGIT + phn1;
									} else if (phn1.length() < 10) {
										phn1 = BR_MA_AREA_CODE + phn1;
									}
									phoneNr = Long.parseLong(phn1);
								}

								for (Long phn : phoneNumbers) {
									if (phn != null && phoneNr != null && phn.compareTo(phoneNr) == 0) {
										phoneNr = null;
										break;
									}
								}
								if (phoneNr != null) {
									phoneNumbers.add(phoneNr);
									phone1.setNumber(phoneNr);
									PhoneDTO phnDto1 = new PhoneDTO(phone1);
									student.getPhones().add(phnDto1);
								}
							}
							break;

						case 17:
							String incomingPhone2 = cell.getRichStringCellValue().toString();
							if (incomingPhone2 != null) {
								Phone phone2 = new Phone();
								String[] phone2Splited = incomingPhone2.split("-");
								StringBuilder phone2Concat = new StringBuilder();
								for (String str : phone2Splited) {
									phone2Concat.append(str);
								}
								String phn2 = phone2Concat.toString();
								Long phoneNr = null;

								if (phn2.matches("\\d{8,11}")) {
									Character c1 = phn2.charAt(0);
									if (c1.toString().compareTo("3") == 0) {
										phn2 = BR_MA_AREA_CODE + phn2;
									} else if (phn2.length() < 9) {
										phn2 = BR_MA_AREA_CODE + NINTH_DIGIT + phn2;
									} else if (phn2.length() < 10) {
										phn2 = BR_MA_AREA_CODE + phn2;
									}
									phoneNr = Long.parseLong(phn2);
								}
								for (Long phn : phoneNumbers) {
									if (phn != null && phoneNr != null && phn.compareTo(phoneNr) == 0) {
										phoneNr = null;
										break;
									}
								}
								if (phoneNr != null) {
									phoneNumbers.add(phoneNr);
									phone2.setNumber(phoneNr);
									PhoneDTO phnDto2 = new PhoneDTO(phone2);
									student.getPhones().add(phnDto2);
								}
							}
							break;

						case 18:
							if (student.getEmail() == null) {
								String email = cell.getRichStringCellValue().toString();
								if (email != null) {
									student.setEmail(email.trim());
								}
							}
							break;

						case 19:
							if (student.getNationality() == null) {
								String nationality = cell.getRichStringCellValue().toString();
								if (nationalId != null) {
									switch (nationality.trim()) {
									case "BRASILEIRA":
										student.setNationality(Nationality.BRAZILIAN);
										break;

									default:
										break;
									}
								}
							}
							break;

						case 20:
							if (student.getBirthPlace() == null) {
								String incomingState = cell.getRichStringCellValue().toString();
								if (incomingState != null) {
									State stt = stateRepository.findByNameLike(incomingState);
									if (stt == null) {
										birthState.setName(incomingState.trim());
										birthState.setCountry(STANDARD_COUNTRY);
										State svStt = stateRepository.save(birthState);
										birthState = svStt;
									} else {
										birthState = stt;
									}
								}
							}
							break;

						case 21:
							if (student.getBirthPlace() == null) {
								String incomingCity = cell.getRichStringCellValue().toString();
								if (incomingCity != null) {
									City ct = cityRepository.findByNameLike(incomingCity);
									if (ct == null) {
										birthPlace.setState(birthState);
										birthPlace.setName(incomingCity.trim());
										City svCt = cityRepository.save(birthPlace);
										student.setBirthPlace(svCt);
									} else {
										birthPlace = ct;
									}
								}
							}
							break;

						case 22:
							if (student.getSocialAssistance() == null) {
								String incomingSocialAssistance = cell.getRichStringCellValue().toString();
								if (incomingSocialAssistance != null) {
									switch (incomingSocialAssistance.trim()) {
									case "SIM":
										student.setSocialAssistance(true);
										break;
									case "NÃO":
										student.setSocialAssistance(false);
										break;
									default:
										break;
									}
								}
							}
							break;

						case 23:
							if (student.getNationalId() == null) {
								String incomingNationalId = cell.getRichStringCellValue().toString();
								if (incomingNationalId != null) {
									String nationalIdAsString = incomingNationalId.substring(1);
									Long nationalIdNumber = null;
									if (nationalIdAsString.matches("\\d{7,13}")) {
										nationalIdNumber = Long.parseLong(nationalIdAsString);
									}

									for (Long nId : nationalIds) {
										if (nId != null && nationalIdNumber != null
												&& nId.compareTo(nationalIdNumber) == 0) {
											nationalIdNumber = null;
											student.setSocialId(nationalIdNumber);
											break;
										}
									}
									nationalIds.add(nationalIdNumber);
									nationalId.setNumber(nationalIdNumber);
								}
							}
							break;

						case 24:
							if (student.getNationalId() == null) {
								String issuingEntity = cell.getRichStringCellValue().toString();
								if (issuingEntity != null) {
									nationalId.setIssuingEntity(issuingEntity.trim());
								}
							}
							break;

						case 25:
							String incomingNationalIdState = cell.getRichStringCellValue().toString();
							if (incomingNationalIdState != null) {
								State stt = stateRepository.findByNameLike(incomingNationalIdState);
								if (stt == null) {
									nationalIdState.setName(incomingNationalIdState.trim());
									nationalIdState.setCountry(STANDARD_COUNTRY);
									State svStt = stateRepository.save(nationalIdState);
									nationalIdState = svStt;
								} else {
									nationalIdState = stt;
								}
							}
							break;

						case 26:
							String incomingNationalIdCity = cell.getRichStringCellValue().toString();
							if (incomingNationalIdCity != null) {
								City ct = cityRepository.findByNameLike(incomingNationalIdCity);
								if (ct == null) {
									nationalIdCity.setName(incomingNationalIdCity.trim());
									nationalIdCity.setState(nationalIdState);
									City scCt = cityRepository.save(nationalIdCity);
									nationalId.setCity(scCt);
								} else {
									nationalId.setCity(ct);
								}
								student.setNationalId(nationalId);
							}
							break;

						case 27:
							if (student.getAddress() == null) {
								String incomingAddress = cell.getRichStringCellValue().toString();
								if (incomingAddress != null) {
									int indexOfDistrict = incomingAddress.indexOf("BAIRRO:");
									int indexOfNumber = incomingAddress.indexOf("Nº:");
									int indexOfComplement = incomingAddress.indexOf("COMP.:");
									String incomingZipCode = incomingAddress.substring(4, indexOfDistrict);

									String[] zipCodeSplited = incomingZipCode.split("-");
									StringBuilder zipCodeConcat = new StringBuilder();
									for (String str : zipCodeSplited) {
										zipCodeConcat.append(str);
									}
									Integer zipCode = zipCodeConcat.toString().length() == 8
											? Integer.parseInt(zipCodeConcat.toString())
											: null;
									address.setZipCode(zipCode);
									address.setDistrict(incomingAddress.substring(indexOfDistrict + 7, indexOfNumber));
									address.setNumber(incomingAddress.substring(indexOfNumber + 3, indexOfComplement));
									address.setComplement(incomingAddress.substring(indexOfComplement + 6).trim());
									address.setCity(null);
									student.setAddress(address);
								}
							}
							break;

						case 28:
							if (student.getRace() == null) {
								String incomingRace = cell.getRichStringCellValue().toString();
								if (incomingRace != null) {
									String[] splitedIncomingRace = incomingRace.split(" ");
									String race = splitedIncomingRace[0];
									switch (race) {
									case "BRANCA":
										student.setRace(Race.WHITE);
										break;
									case "NEGRA":
										student.setRace(Race.BLACK);
										break;
									case "PARDA":
										student.setRace(Race.BROWN);
										break;
									case "AMARELA":
										student.setRace(Race.ASIAN);
										break;
									case "INDÍGENA":
										student.setRace(Race.NATIVE);
										break;
									default:
										student.setRace(Race.NOT_DECLARED);
										break;
									}
								}
							}
							break;

						case 29:
							if (student.getDisability() == null) {
								String disability = cell.getRichStringCellValue().toString();
								if (disability != null) {
									student.setDisability(disability.trim());
								}
							}
							break;

						default:
							break;
						}
					}
				}
				Long checkEnrollment = student.getEnrollment();
				if (checkEnrollment != null) {
					students.add(student);
				}
			}

			for (StudentDTO stdDto : students) {
				studentService.insert(stdDto);
			}

			workbook.close();

			LOG.info("File read successfully");

		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	private boolean checkSocialIdUniqueness(Long socialId) {
		return studentRepository.existsStudentBySocialId(socialId);
	}
}
