import { ReactComponent as AddIcon } from "assets/images/add-icon.svg";
import { Controller, useForm } from "react-hook-form";
import { useParams } from "react-router-dom";
import { SchoolClass } from "types/schoolClass";
import { useCallback, useEffect } from "react";
import { requestBackend } from "util/requests";
import { AxiosRequestConfig } from "axios";
import { history } from "util/history";
import { useState } from "react";
import { getEnumKeys, getPeriod_PT_BR } from "util/helpers";
import { toast } from "react-toastify";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";
import { Period } from "types/enums/period";
import Select from "react-select";
import { Phase } from "types/phase";
import AddPhaseModal from "./AddPhaseModal";

import "./styles.css";

type UrlParams = {
  schoolClassId: string;
};

const SchoolClassForm = () => {
  const { schoolClassId } = useParams<UrlParams>();
  const [isLoading, setIsLoading] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [phases, setPhases] = useState<Phase[]>();
  const [isOpen, setIsOpen] = useState(false);

  const isEditing = schoolClassId !== "create";

  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    control,
  } = useForm<SchoolClass>();

  // Get Phases
  const getPhases = useCallback(() => {
    requestBackend({ url: "/phases", withCredentials: true }).then(
      (response) => {
        setPhases(response.data);
      }
    );
  }, []);

  useEffect(() => {
    getPhases();
  }, [getPhases]);

  useEffect(() => {
    if (isEditing) {
      setIsLoading(true);
      requestBackend({
        url: `/school-classes/${schoolClassId}`,
        withCredentials: true,
      })
        .then((response) => {
          const schoolClass = response.data as SchoolClass;
          setValue("name", schoolClass.name);
          setValue("period", schoolClass.period);
          setValue("phase", schoolClass.phase);
        })
        .finally(() => {
          setIsLoading(false);
        });
    }
  }, [isEditing, schoolClassId, setValue]);

  const onSubmit = (formData: SchoolClass) => {
    setIsProcessing(true);
    const data = formData;

    const config: AxiosRequestConfig = {
      method: isEditing ? "PUT" : "POST",
      url: isEditing ? `/school-classes/${schoolClassId}` : "/school-classes",
      data,
      withCredentials: true,
    };

    requestBackend(config).then(() => {
      isEditing
        ? toast.info("Turma atualizada com sucesso.")
        : toast.info("Turma cadastrada com sucesso.");
      history.push("/admin/schoolClasses");
    });
  };

  const handleCancel = () => {
    history.push("/admin/schoolClasses");
  };

  return (
    <form className="container mb-4 py-lg-3" onSubmit={handleSubmit(onSubmit)}>
      {isLoading || isProcessing ? (
        <AppLoader isProcessing={isProcessing} />
      ) : (
        <div className="container">
          <div className="row border border-opacity-10 rounded mx-auto p-3 scform-ctr">
            <div className="d-flex align-items-center justify-content-between">
              <h2 className="form-title">
                {isEditing ? "Atualizar" : "Cadastrar"} Turma
              </h2>
              <GoBackButton />
            </div>

            <div className="col-12">
              <label htmlFor="name" className="form-label">
                Nome
              </label>
              <input
                {...register("name", {
                  required: "Campo obrigatório",
                })}
                type={"text"}
                className={`form-control base-input ${
                  errors.name ? "is-invalid" : ""
                }`}
                placeholder="Nome identificador da Turma"
                id="name"
              />
              <div className="invalid-feedback">{errors.name?.message}</div>
            </div>

            <div className="mt-3 col-12">
              <label htmlFor="period" className="form-label">
                Período
              </label>
              <select {...register("period")} className="period-select">
                <option value="" key="" disabled selected hidden>
                  Escolha um Período
                </option>
                {getEnumKeys(Period).map((key, index) => (
                  <option value={Period[key]} key={index}>
                    {getPeriod_PT_BR(key)}
                  </option>
                ))}
              </select>
              <span className="text-danger">{errors.period?.message}</span>
            </div>

            <div className="row mt-3">
              <div className="col-12 col-sm-10">
                <label htmlFor="phase" className="form-label">
                  Etapa
                </label>
                <Controller
                  name="phase"
                  rules={{ required: true }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      options={phases}
                      classNamePrefix="custom-select"
                      getOptionLabel={(phs) => phs.description}
                      getOptionValue={(phs) => String(phs.id)}
                      inputId="phase"
                      placeholder="Escolha uma Etapa"
                      isClearable
                    />
                  )}
                />
                {errors.phase && (
                  <div className="invalid-feedback d-block">
                    Campo obrigatório
                  </div>
                )}
              </div>
              <div
                onClick={() => setIsOpen(true)}
                className="mt-2 mt-sm-0 col-4 col-sm-2 add-phase-btn-ctr"
              >
                <span className="add-phase-tooltip-text">Adicionar Etapa</span>
                <AddIcon />
              </div>
            </div>

            <hr className="my-4 separator" />

            <div className="col-12 d-flex justify-content-between justify-content-md-around justify-content-lg-end">
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
      <AddPhaseModal
        open={isOpen}
        setOpen={setIsOpen}
        updatePhases={getPhases}
        onClose={() => setIsOpen(false)}
      />
    </form>
  );
};

export default SchoolClassForm;
