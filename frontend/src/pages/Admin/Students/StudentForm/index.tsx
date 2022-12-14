import { ReactComponent as AddIcon } from "assets/images/add-icon.svg";
import axios, { AxiosRequestConfig } from "axios";
import { useCallback, useEffect, useState } from "react";
import { useForm, Controller } from "react-hook-form";
import { useParams } from "react-router-dom";
import { requestBackend } from "util/requests";
import { SchoolClass } from "types/schoolClass";
import { history } from "util/history";
import { Student } from "types/student";
import { toast } from "react-toastify";
import { City } from "types/city";
import GoBackButton from "components/GoBackButton";
import Select from "react-select";
import {
  maskSocialIdNumber,
  maskPhoneNumber,
  maskZipCodeNumber,
} from "util/maskers";
import AppLoader from "components/AppLoader";
import AddCityModal from "./AddCityModal";
import {
  getEnumKeys,
  getGender_PT_BR,
  getNationality_PT_BR,
  getPeriod_PT_BR,
  getRace_PT_BR,
} from "util/helpers";
import { Gender } from "types/enums/gender";
import { Nationality } from "types/enums/nationality";
import { Race } from "types/enums/race";
import { ViaCep } from "types/viacep";
import { Phone } from "types/phone";

import "./styles.css";

type UrlParams = {
  studentId: string;
};

const StudentForm = () => {
  const [cities, setCities] = useState<City[]>([]);
  const [schoolClasses, setSchoolClasses] = useState<SchoolClass[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [selectedRadioBtn, setSelectedRadioBtn] = useState("");

  const { studentId } = useParams<UrlParams>();

  const isEditing = studentId !== "create";

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    control,
  } = useForm<Student>();

  // Get Cities
  const getCities = useCallback(() => {
    requestBackend({ url: "/cities", withCredentials: true }).then(
      (response) => {
        setCities(response.data);
      }
    );
  }, []);

  useEffect(() => {
    getCities();
  }, [getCities]);

  // Get School Classes
  useEffect(() => {
    requestBackend({ url: "/school-classes", withCredentials: true }).then(
      (response) => {
        setSchoolClasses(response.data.content);
      }
    );
  }, []);

  const handleZipCodeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = String(event.target.value).replace(/[^0-9]/g, "");
    if (value.length === 8) {
      const cep = value;
      axios({
        url: `https://viacep.com.br/ws/${cep}/json/`,
      }).then((response) => {
        const viaCepData: ViaCep = response.data;
        setValue("address.complement", viaCepData.complemento);
        setValue(
          "address.city",
          cities?.reduce((obj) =>
            obj["name"] === viaCepData.localidade
              ? obj
              : { id: 0, name: "", state: { id: 0, name: "", country: "" } }
          )
        );
        setValue("address.complement", viaCepData.complemento);
        setValue("address.district", viaCepData.bairro);
      });
    }
    event.target.value = maskZipCodeNumber(event.target.value);
  };

  //Get Student for editing
  useEffect(() => {
    if (isEditing) {
      setIsLoading(true);
      requestBackend({
        url: `/students/${studentId}`,
        withCredentials: true,
      }).then((studentResponse) => {
        const student = studentResponse.data as Student;
        setValue("name", student.name);
        setValue("lastName", student.lastName);
        setValue("socialId", student.socialId);
        setValue("birthDate", student.birthDate);
        setValue("enrollment", student.enrollment);
        setValue("gender", student.gender);
        setValue("email", student.email);
        setValue("nationality", student.nationality);
        setValue("birthPlace", student.birthPlace);
        setValue("nationalId", student.nationalId);
        setValue("race", student.race);
        setValue("disability", student.disability);
        setValue("parents.0", student.parents[0]);
        setValue("parents.1", student.parents[1]);
        setValue("phones.0", student.phones[0]);
        setValue("phones.1", student.phones[1]);
        setValue("address", student.address);
        setValue("schoolClass", student.schoolClass);
        setSelectedRadioBtn(student.socialAssistance.valueOf().toString());
        setIsLoading(false);
      });
    }
  }, [isEditing, setValue, studentId]);

  const isRadioSelected = (value: string): boolean =>
    selectedRadioBtn === value;

  const handleRadioClick = (event: React.ChangeEvent<HTMLInputElement>): void =>
    setSelectedRadioBtn(event.target.value);

  const onSubmit = (formData: Student) => {
    setIsProcessing(true);

    const phones: Phone[] = [];
    formData.phones
      .map((phn) => phn.number.toString().replace(/[^0-9]/g, ""))
      .forEach((phn) => phones.push({ number: parseInt(phn) }));

    const data = {
      ...formData,
      socialId: formData.socialId.toString().replace(/[^0-9]/g, ""),
      address: {
        ...formData.address,
        zipCode: formData.address.zipCode.toString().replace(/[^0-9]/g, ""),
      },
      phones,
    };

    const config: AxiosRequestConfig = {
      method: isEditing ? "PUT" : "POST",
      url: isEditing ? `/students/${studentId}` : "/students",
      data,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        isEditing
          ? toast.info(
              "As informações do aluno(a) foram atualizadas com sucesso."
            )
          : toast.info("Aluno(a) cadastrado com sucesso.");
        history.push("/admin/students");
      })
      .catch(() => {
        setIsProcessing(false);
        toast.error("Erro ao cadastrar aluno");
      });
  };

  const handleCancel = () => {
    history.push("/admin/students");
  };

  return (
    <form className="mb-4 p-lg-3" onSubmit={handleSubmit(onSubmit)}>
      {isLoading || isProcessing ? (
        <AppLoader isProcessing={isProcessing} />
      ) : (
        <div className="container">
          <div className="border border-opacity-10 rounded p-2 px-sm-3">
            {/* Student data session */}
            <div className="row g-3 p-lg-2">
              <div className="d-flex align-items-center justify-content-between">
                <h2 className="form-title">Dados do aluno</h2>
                <GoBackButton />
              </div>
              <div className="col-12 col-sm-6">
                <label htmlFor="name" className="form-label">
                  Nome
                </label>
                <input
                  {...register("name", { required: "Campo obrigatório" })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.name ? "is-invalid" : ""
                  }`}
                  id="name"
                  placeholder="Digite o primeiro nome do aluno"
                />
                {errors.name && (
                  <div className="invalid-feedback">{errors.name?.message}</div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="lastName" className="form-label">
                  Sobrenome
                </label>
                <input
                  {...register("lastName", { required: "Campo obrigatório" })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.lastName ? "is-invalid" : ""
                  }`}
                  id="lastName"
                  placeholder="Digite o(s) sobrenome(s) do aluno"
                />
                {errors.lastName && (
                  <div className="invalid-feedback">
                    {errors.lastName.message}
                  </div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="socialId" className="form-label">
                  CPF
                </label>
                <div className="input-group has-validation">
                  <input
                    {...register("socialId", {
                      onChange(event) {
                        event.target.value = maskSocialIdNumber(
                          event.target.value
                        );
                      },
                    })}
                    type={"text"}
                    id="socialId"
                    maxLength={14}
                    placeholder="Digite o CPF nome do aluno"
                    className="form-control base-input"
                  />
                </div>
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="birthDate" className="form-label">
                  Data de nascimento
                </label>
                <input
                  {...register("birthDate", { required: true })}
                  type={"date"}
                  className={`form-control base-input ${
                    errors.birthDate ? "is-invalid" : ""
                  }`}
                  id="birthDate"
                />
                {errors.birthDate && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="enrollment" className="form-label">
                  Nº Matrícula
                </label>
                <input
                  {...register("enrollment", {
                    required: "Campo obrigatório",
                    onChange(event) {
                      event.target.value = event.target.value.replace(
                        /\D/g,
                        ""
                      );
                    },
                  })}
                  type={"text"}
                  inputMode={"numeric"}
                  className={`form-control base-input ${
                    errors.enrollment ? "is-invalid" : ""
                  }`}
                  id="enrollment"
                  placeholder="Informe a matrícula do aluno"
                  maxLength={8}
                />
                {errors.enrollment && (
                  <div className="invalid-feedback">
                    {errors.enrollment.message}
                  </div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="gender" className="form-label">
                  Gênero
                </label>
                <select
                  {...register("gender", { required: true })}
                  className={`enums-select ${
                    errors.gender ? "border border-danger" : ""
                  }`}
                  id="gender"
                >
                  <option value="" key="" disabled selected hidden>
                    Escolha um Gênero
                  </option>
                  {getEnumKeys(Gender).map((key, index) => (
                    <option value={Gender[key]} key={index}>
                      {getGender_PT_BR(key)}
                    </option>
                  ))}
                </select>
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="email" className="form-label">
                  E-mail
                </label>
                <input
                  {...register("email", {
                    pattern:
                      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
                  })}
                  type={"email"}
                  name="email"
                  id="email"
                  className={`form-control base-input ${
                    errors.email ? "is-invalid" : ""
                  }`}
                  placeholder="Digite o e-mail do aluno"
                />
                {errors.email?.type && (
                  <div className="invalid-feedback">
                    Digite um e-mail válido.
                  </div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="nationality" className="form-label">
                  Nacionalidade
                </label>
                <select
                  {...register("nationality", { required: true })}
                  className={`enums-select ${
                    errors.nationality ? "border border-danger" : ""
                  }`}
                  id="nationality"
                >
                  <option value="" key="" disabled selected hidden>
                    Escolha uma Nacionalidade
                  </option>
                  {getEnumKeys(Nationality).map((key, index) => (
                    <option value={Nationality[key]} key={index}>
                      {getNationality_PT_BR(key)}
                    </option>
                  ))}
                </select>
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="birthPlace" className="form-label">
                  Local de nascimento
                </label>
                <Controller
                  name="birthPlace"
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={cities}
                      classNamePrefix="custom-select"
                      getOptionLabel={(city) =>
                        city.name + ", " + city.state.name
                      }
                      getOptionValue={(city) => String(city.id)}
                      inputId="birthPlace"
                      placeholder="Escolha o local de nascimento"
                      isClearable
                    />
                  )}
                />
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="socialAssistance" className="form-label">
                  Recebe bolsa família?
                </label>
                <div className="d-flex justify-content-evenly">
                  <div className="form-check mb-0 mt-3">
                    <input
                      {...register("socialAssistance", { required: true })}
                      type={"radio"}
                      name="socialAssistance"
                      id="socialAssistance.true"
                      value={"true"}
                      className="form-check-input"
                      checked={isRadioSelected("true")}
                      onChange={handleRadioClick}
                    />
                    <label
                      htmlFor="socialAssistance.true"
                      className="form-check-label m-0"
                    >
                      Sim
                    </label>
                  </div>
                  <div className="form-check mb-0 mt-3">
                    <input
                      {...register("socialAssistance", { required: true })}
                      type={"radio"}
                      name="socialAssistance"
                      id="socialAssistance.false"
                      value={"false"}
                      className="form-check-input"
                      checked={isRadioSelected("false")}
                      onChange={handleRadioClick}
                    />
                    <label
                      htmlFor="socialAssistance.false"
                      className="form-check-label m-0"
                    >
                      Não
                    </label>
                  </div>
                </div>
              </div>

              <div className="border p-3 row mx-auto mt-3">
                <div className="col-12 col-sm-6">
                  <label htmlFor="nationalId.number" className="form-label">
                    RG
                  </label>
                  <input
                    {...register("nationalId.number", {
                      onChange: (event) =>
                        (event.target.value = event.target.value.replace(
                          /[^0-9]/g,
                          ""
                        )),
                    })}
                    type={"text"}
                    inputMode={"numeric"}
                    name="nationalId.number"
                    id="nationalId.number"
                    className="form-control base-input"
                    placeholder="Número do RG"
                    maxLength={13}
                  />
                </div>

                <div className="col-12 col-sm-6">
                  <label
                    htmlFor="nationalId.issuingEntity"
                    className="form-label"
                  >
                    Órgão Emissor
                  </label>
                  <input
                    {...register("nationalId.issuingEntity")}
                    type={"text"}
                    name="nationalId.issuingEntity"
                    id="nationalId.issuingEntity"
                    className="form-control base-input"
                    placeholder="Órgão Emissor"
                  />
                </div>

                <div className="col-12 col-sm-6 mt-3">
                  <label htmlFor="nationalId.city" className="form-label">
                    Cidade expedidora
                  </label>
                  <Controller
                    name="nationalId.city"
                    control={control}
                    render={({ field }) => (
                      <Select
                        {...field}
                        options={cities}
                        classNamePrefix="custom-select"
                        getOptionLabel={(city) =>
                          city.name + ", " + city.state.name
                        }
                        getOptionValue={(city) => String(city.id)}
                        inputId="nationalId.city"
                        placeholder="Cidade expedidora"
                        isClearable
                      />
                    )}
                  />
                </div>
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="race" className="form-label">
                  Raça / Etnia
                </label>
                <select
                  {...register("race", { required: true })}
                  className={`enums-select ${
                    errors.race ? "border border-danger" : ""
                  }`}
                  id="race"
                >
                  <option value="" key="" disabled selected hidden>
                    Escolha uma Raça / etnia
                  </option>
                  {getEnumKeys(Race).map((key, index) => (
                    <option value={Race[key]} key={index}>
                      {getRace_PT_BR(key)}
                    </option>
                  ))}
                </select>
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="disability" className="form-label">
                  Deficiência
                </label>
                <input
                  {...register("disability")}
                  type={"text"}
                  name="disability"
                  id="disability"
                  className="form-control base-input"
                  placeholder="Deficiência"
                />
              </div>
            </div>

            <hr className="my-4" />

            {/* Parent data session */}
            <div className="row g-3 p-lg-2 mt-2">
              <h2 className="form-title">Dados dos responsáveis</h2>

              <div className="border p-3 row mx-auto mt-3">
                <p>Mãe</p>
                <div className="col-12 col-sm-6">
                  <input
                    {...register("parents.0.name" as const, {
                      required: true,
                    })}
                    type={"text"}
                    className={`form-control base-input ${
                      errors.parents ? "is-invalid" : ""
                    }`}
                    id={"parents.0.name"}
                    placeholder="Nome"
                  />
                  {errors.parents && (
                    <div className="invalid-feedback">Campo obrigatório</div>
                  )}
                </div>
                <div className="col-12 col-sm-6">
                  <input
                    {...register("parents.0.lastName" as const, {
                      required: true,
                    })}
                    type={"text"}
                    className={`form-control base-input ${
                      errors.parents ? "is-invalid" : ""
                    }`}
                    id={"parents.0.lastName"}
                    placeholder="Sobrenome"
                  />
                  {errors.parents && (
                    <div className="invalid-feedback">Campo obrigatório</div>
                  )}
                </div>
              </div>

              <div className="border p-3 row mx-auto mt-3">
                <p>Pai</p>
                <div className="col-12 col-sm-6">
                  <input
                    {...register("parents.1.name" as const)}
                    type={"text"}
                    className="form-control base-input"
                    id={"parents.1.name"}
                    placeholder="Nome"
                  />
                </div>
                <div className="col-12 col-sm-6">
                  <input
                    {...register("parents.1.lastName" as const)}
                    type={"text"}
                    className="form-control base-input"
                    id={"parents.1.lastName"}
                    placeholder="Sobrenome"
                  />
                </div>
              </div>
            </div>

            {/* Phones session */}
            <div className="row g-3 p-lg-2 mt-2">
              <h2 className="form-title">Telefones</h2>
              <div className="col-12 col-sm-6">
                <input
                  {...register("phones.0.number", {
                    onChange(event) {
                      event.target.value = maskPhoneNumber(event.target.value);
                    },
                  })}
                  type={"tel"}
                  className="form-control base-input"
                  id={"phones.0.number"}
                  placeholder="Informe um telefone para contato"
                  maxLength={16}
                />
              </div>
              <div className="col-12 col-sm-6">
                <input
                  {...register("phones.1.number", {
                    onChange(event) {
                      event.target.value = maskPhoneNumber(event.target.value);
                    },
                  })}
                  type={"tel"}
                  className="form-control base-input"
                  id={"phones.1.number"}
                  placeholder="Outro telefone para contato"
                  maxLength={16}
                />
              </div>
            </div>

            <hr className="my-4" />

            {/* Address session */}
            <div className="row g-3 p-lg-2 mt-2">
              <h2 className="form-title">Endereço</h2>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.zipCode" className="form-label">
                  CEP
                </label>

                <input
                  {...register("address.zipCode", {
                    onChange(event) {
                      handleZipCodeChange(event);
                    },
                  })}
                  type={"text"}
                  className="form-control base-input"
                  id="address.zipCode"
                  placeholder="Digite o CEP"
                  maxLength={10}
                />
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.district" className="form-label">
                  Bairro
                </label>
                <input
                  {...register("address.district")}
                  type={"text"}
                  className="form-control base-input"
                  id="address.district"
                  placeholder="Digite o Bairro"
                />
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.number" className="form-label">
                  Número
                </label>
                <input
                  {...register("address.number")}
                  type={"text"}
                  className="form-control base-input"
                  id="address.number"
                  placeholder="Nº ..."
                />
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.complement" className="form-label">
                  Complemento
                </label>
                <input
                  {...register("address.complement")}
                  type={"text"}
                  className="form-control base-input"
                  id="address.complement"
                  placeholder="Quadra, bloco..."
                />
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.city" className="form-label">
                  Município
                </label>
                <Controller
                  name="address.city"
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={cities}
                      classNamePrefix="custom-select"
                      getOptionLabel={(city) => city.name}
                      getOptionValue={(city) => String(city.id)}
                      inputId="address.city"
                      placeholder="Município"
                      isClearable
                    />
                  )}
                />
              </div>

              <div
                onClick={() => setIsOpen(true)}
                className="col-3 col-sm-2 add-county-btn-ctr"
              >
                <span className="add-county-tooltip-text">
                  Adicionar Município
                </span>
                <AddIcon />
              </div>
            </div>

            <hr className="my-4" />

            {/* School Class session */}

            <div className="row g-3 p-lg-2 mt-2">
              <h2 className="form-title">Turma</h2>
              <div className="school-class-custom-ctr">
                <label htmlFor="schoolClass" className="form-label">
                  Identificador
                </label>
                <Controller
                  name="schoolClass"
                  rules={{ required: true }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={schoolClasses}
                      classNamePrefix="custom-select"
                      getOptionLabel={(sc) =>
                        sc.name + " - " + getPeriod_PT_BR(sc.period)
                      }
                      getOptionValue={(sc) => String(sc.id)}
                      inputId="schoolClass"
                      placeholder="Turma"
                    />
                  )}
                />
                {errors.schoolClass && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório. Escolha uma turma.
                  </div>
                )}
              </div>
            </div>

            <hr className="my-4" />

            {/* Buttons */}
            <div className="mb-4 col-12 d-flex justify-content-between justify-content-md-around justify-content-lg-end">
              <button
                className="btn btn-outline-danger custom-btn me-2 me-lg-5"
                onClick={handleCancel}
              >
                Cancelar
              </button>
              <button
                type="submit"
                className="text-white btn btn-primary custom-btn"
              >
                Salvar
              </button>
            </div>
          </div>
        </div>
      )}
      <AddCityModal
        open={isOpen}
        setOpen={setIsOpen}
        updateCounties={getCities}
        onClose={() => setIsOpen(false)}
      />
    </form>
  );
};

export default StudentForm;
