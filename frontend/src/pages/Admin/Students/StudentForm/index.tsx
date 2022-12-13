import { ReactComponent as AddIcon } from "assets/images/add-icon.svg";
import { AxiosRequestConfig } from "axios";
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
  maskPhoneNumber
} from "util/maskers";
import AppLoader from "components/AppLoader";
import AddCityModal from "./AddCityModal";

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
        setCities(response.data.content);
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
        console.log(schoolClasses);
        
      }
    );
  }, []);

  /*
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
          "address.county",
          counties?.reduce((obj) =>
            obj["name"] === viaCepData.localidade
              ? obj
              : { id: 0, name: "", state: "" }
          )
        );
        setValue("address.county.state", viaCepData.uf);
        setValue("address.complement", viaCepData.complemento);
        setValue("address.publicPlace", viaCepData.logradouro);
        setValue("address.district", viaCepData.bairro);
      });
    }
    event.target.value = maskZipCodeNumber(event.target.value);
  };
  */

  //Get Student for editing
  useEffect(() => {
    if (isEditing) {
      setIsLoading(true);
      requestBackend({
        url: `/students/${studentId}`,
        withCredentials: true,
      }).then((studentResponse) => {
        const student = studentResponse.data as Student;
        console.log(student);






        
      });
    }
  }, [isEditing, setValue, studentId]);

  const onSubmit = (formData: Student) => {
    setIsProcessing(true);
    const data = formData;

    const config: AxiosRequestConfig = {
      method: isEditing ? "PUT" : "POST",
      url: isEditing ? `/students/${studentId}` : "/students",
      data,
      withCredentials: true,
    };

    requestBackend(config).then(() => {
      isEditing
        ? toast.info(
            "As informações do aluno(a) foram atualizadas com sucesso."
          )
        : toast.info("Aluno(a) cadastrado com sucesso.");
      history.push("/admin/students");
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
                <label htmlFor="cpf" className="form-label">
                  CPF
                </label>
                <div className="input-group has-validation">
                  <input
                    {...register("socialId", {
                      onChange(event) {
                        event.target.value = maskSocialIdNumber(event.target.value);
                      },
                    })}
                    type={"text"}
                    id="cpf"
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
                    required: true,
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
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>
            </div>

            <hr className="my-4" />

            {/* Parent data session */}
            <div className="row g-3 p-lg-2 mt-2">
              <h2 className="form-title">Dados do responsável</h2>
              <div className="col-12 col-sm-6">
                <label htmlFor="parent.name" className="form-label">
                  Nome
                </label>
                <input
                  {...register("parents", { required: true })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.parents ? "is-invalid" : ""
                  }`}
                  id="parent.name"
                  placeholder="Digite o primeiro nome do responsável pelo aluno"
                />
                {errors.parents && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="parent.lastName" className="form-label">
                  Sobrenome
                </label>
                <input
                  {...register("parents", { required: true })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.parents ? "is-invalid" : ""
                  }`}
                  id="parent.lastName"
                  placeholder="Digite o(s) sobrenome(s) do responsável pelo aluno"
                />
                {errors.parents && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>

              <div className="col-12 col-sm-6">
                <label htmlFor="phones" className="form-label">
                  Telefone
                </label>
                <input
                  {...register("phones", {
                    onChange(event) {
                      event.target.value = maskPhoneNumber(event.target.value);
                    },
                  })}
                  type={"tel"}
                  className="form-control base-input"
                  id="phones"
                  placeholder="Informe um telefone para contato"
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
                    required: true,
                    //onChange(event) {
                    //  handleZipCodeChange(event);
                    //},
                  })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.address?.zipCode ? "is-invalid" : ""
                  }`}
                  id="address.zipCode"
                  placeholder="65.000-000"
                  maxLength={10}
                />
                {errors.address?.zipCode && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.district" className="form-label">
                  Bairro
                </label>
                <input
                  {...register("address.district", { required: true })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.address?.district ? "is-invalid" : ""
                  }`}
                  id="address.district"
                  placeholder="Cohatrac, Trizidela..."
                />
                {errors.address?.district && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
              </div>

              <div className="col-12 col-sm-4">
                <label htmlFor="address.number" className="form-label">
                  Número
                </label>
                <input
                  {...register("address.number", { required: true })}
                  type={"text"}
                  className={`form-control base-input ${
                    errors.address?.number ? "is-invalid" : ""
                  }`}
                  id="address.number"
                  placeholder="Nº ..."
                />
                {errors.address?.number && (
                  <div className="invalid-feedback">Campo obrigatório.</div>
                )}
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
                <label htmlFor="address.county" className="form-label">
                  Município
                </label>
                <Controller
                  name="address.city"
                  rules={{ required: true }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={cities}
                      classNamePrefix="custom-select"
                      getOptionLabel={(county) => county.name}
                      getOptionValue={(county) => String(county.id)}
                      inputId="address.county"
                      placeholder="Município"
                      isClearable
                    />
                  )}
                />
                {errors.address?.city && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório
                  </div>
                )}
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
                      getOptionLabel={(sc) => sc.name + " - " + sc.period}
                      getOptionValue={(sc) => String(sc.id!)}
                      inputId="schoolClass"
                      placeholder="Turma"
                    />
                  )}
                />
                {errors.schoolClass && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório
                  </div>
                )}
              </div>
            </div>
*/}
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
