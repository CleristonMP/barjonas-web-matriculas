import {
  formatCep,
  formatSocialId,
  formatDate,
  formatPhoneNumber,
} from "util/formatters";
import {
  getGender_PT_BR,
  getNationality_PT_BR,
  getPeriod_PT_BR,
  getRace_PT_BR,
} from "util/helpers";
import { useEffect, useState } from "react";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { useParams } from "react-router-dom";
import { history } from "util/history";
import { Student } from "types/student";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";
import AppModal from "components/AppModal";

import "./styles.css";

type UrlParams = {
  studentId: string;
};

const StudentDetails = () => {
  const { studentId } = useParams<UrlParams>();
  const [student, setStudent] = useState<Student>();
  const [isLoading, setIsLoading] = useState(false);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const config: AxiosRequestConfig = {
      url: `/students/${studentId}`,
      withCredentials: true,
    };

    setIsLoading(true);
    requestBackend(config).then((response) => {
      const student = response.data as Student;
      setStudent(student);
      setIsLoading(false);
    });
  }, [studentId]);

  const handleDelete = (enrollment: number) => {
    setOpen(false);

    const config: AxiosRequestConfig = {
      method: "DELETE",
      url: `/students/${enrollment}`,
      withCredentials: true,
    };

    if (student) {
      requestBackend(config)
        .then(() => {
          history.push("/admin/students");
          toast.info(
            `O(a) aluno(a) ${
              student.name +
              " " +
              student.lastName +
              " - Matrícula: " +
              student.enrollment
            } foi excluído(a) com sucesso.`
          );
        })
        .catch(() => {
          toast.error("Erro ao excluir aluno(a).");
        });
    }
  };

  return (
    <div className="container mt-3 mb-5 py-lg-3">
      {isLoading ? (
        <AppLoader />
      ) : (
        <div className="card base-card std-details-card">
          <div className="card-body p-4">
            <div className="mb-2 d-flex align-items-center justify-content-between">
              <h2 className="card-title m-0">
                {student?.name + " " + student?.lastName}
              </h2>
              <GoBackButton />
            </div>
            <h5 className="card-subtitle mb-2">
              Matrícula: {student?.enrollment}
            </h5>
            <p className="card-text">
              <span className="fw-bold">Turma: </span>
              {student?.schoolClass.name +
                " - " +
                getPeriod_PT_BR(student?.schoolClass.period!)}
            </p>
            <p className="card-text">
              <span className="fw-bold">Etapa: </span>
              {student?.schoolClass.phase.description}
            </p>
            <p className="card-text">
              <span className="fw-bold me-1">Data de nascimento:</span>
              <span className="me-2">
                {student?.birthDate
                  ? formatDate(student.birthDate)
                  : "-- / -- / ----"}
              </span>
              &#124;
              <span className="fw-bold ms-2">Local de nascimento: </span>
              {student?.birthPlace
                ? student.birthPlace.name + ", " + student.birthPlace.state.name
                : "Não cadastrado"}
            </p>

            <p className="card-text">
              <span className="fw-bold">CPF: </span>
              <span className="me-2">
                {student?.socialId
                  ? formatSocialId(student.socialId.toString())
                  : "-"}
              </span>
              &#124;
              <span className="fw-bold ms-2">RG: </span>
              {student?.nationalId.number &&
              student.nationalId.issuingEntity &&
              student.nationalId.city ? (
                <span>
                  {student.nationalId.number +
                    ", " +
                    student.nationalId.issuingEntity +
                    ", " +
                    student.nationalId.city +
                    ", " +
                    student.nationalId.city.state}
                </span>
              ) : student?.nationalId.number &&
                student.nationalId.issuingEntity ? (
                <span>
                  {student.nationalId.number +
                    ", " +
                    student.nationalId.issuingEntity}
                </span>
              ) : student?.nationalId.number ? (
                <span>{student.nationalId.number}</span>
              ) : (
                "Não cadastrado"
              )}
            </p>

            <p className="card-text">
              <span className="fw-bold">Gênero:</span>{" "}
              <span className="me-2">
                {student?.gender
                  ? getGender_PT_BR(student.gender)
                  : "Não informado"}
              </span>
              &#124;
              <span className="fw-bold ms-2">Raça/Etnia:</span>{" "}
              <span className="me-2">
                {student?.race ? getRace_PT_BR(student.race) : "Não informado"}
              </span>
              &#124;
              <span className="fw-bold ms-2">Nacionalidade:</span>{" "}
              {student?.nationality
                ? getNationality_PT_BR(student.nationality)
                : "Não informado"}
            </p>

            <p className="card-text">
              <span className="fw-bold">Recebe Bolsa Família? </span>
              <span className="me-2">
                {student?.socialAssistance != null
                  ? student.socialAssistance
                    ? "Sim"
                    : "Não"
                  : "Não informado"}
              </span>
              &#124;
              <span className="fw-bold ms-2">Deficiência: </span>
              {student?.disability ? student.disability : "Nenhuma"}
            </p>

            <p className="card-text">
              <span className="fw-bold me-1">Email:</span>
              {student?.email ? student.email : "Não possui"}
            </p>

            <p className="card-text">
              <span className="fw-bold">Responsáveis:</span>{" "}
              {student?.parents[0] != null ? (
                <span className="me-2">
                  {student?.parents[0].name +
                    " " +
                    student?.parents[0].lastName}
                </span>
              ) : (
                ""
              )}
              {student?.parents !== undefined && student?.parents.length > 1 ? (
                <span>&#47;</span>
              ) : (
                ""
              )}
              {student?.parents[1] != null ? (
                <span className="ms-2">
                  {student?.parents[1].name +
                    " " +
                    student?.parents[1].lastName}
                </span>
              ) : (
                ""
              )}
            </p>
            <p>
              <span className="fw-bold"> Telefones:</span>{" "}
              {student?.phones[0] != null ? (
                <span className="me-2">
                  {formatPhoneNumber(student.phones[0].number.toString())}
                </span>
              ) : (
                ""
              )}
              {student?.phones !== undefined && student?.phones.length > 1 ? (
                <span>&#47;</span>
              ) : (
                ""
              )}
              {student?.phones[1] != null ? (
                <span className="ms-2">
                  {formatPhoneNumber(student.phones[1].number.toString())}
                </span>
              ) : (
                ""
              )}
            </p>

            <p className="card-text">
              <span className="fw-bold">Endereço:</span> CEP:{" "}
              {student?.address.zipCode ? (
                formatCep(student.address.zipCode.toString())
              ) : (
                <></>
              )}
              , Compl.: {student?.address.complement}, nº: {student?.address.number},
              Bairro: {student?.address.district}{" "}
              {student?.address.city
                ? ", " +
                  student?.address.city.name +
                  "-" +
                  student?.address.city.state.name
                : ""}
            </p>

            <div className="mt-4 mt-sm-5 d-flex justify-content-between justify-content-sm-around">
              <Link to={`form`} className="card-link">
                <button
                  type="button"
                  className="btn btn-outline-secondary custom-btn me-2"
                >
                  Editar
                </button>
              </Link>
              <button
                onClick={() => setOpen(true)}
                type="button"
                className="btn btn-outline-danger custom-btn"
              >
                Excluir
              </button>
            </div>
          </div>
        </div>
      )}
      <AppModal
        open={open}
        onClose={() => setOpen(false)}
        text="Tem certeza de que deseja excluir este aluno(a)?"
        onConfirmation={() => handleDelete(student?.enrollment!)}
      />
    </div>
  );
};

export default StudentDetails;
