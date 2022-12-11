import {
  formatCep,
  formatCpf,
  formatDate,
} from "util/formatters";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { Student } from "types/student";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { history } from "util/history";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";
import AppModal from "components/AppModal";

import "./styles.css";

type UrlParams = {
  enrollment: string;
};

const StudentDetails = () => {
  const { enrollment } = useParams<UrlParams>();

  const [student, setStudent] = useState<Student>();
  const [isLoading, setIsLoading] = useState(false);
  const [open, setOpen] = useState(false);

  /* Get Student */
  useEffect(() => {
    const studentRequestConfig: AxiosRequestConfig = {
      url: `/students/${enrollment}`,
      withCredentials: true,
    };

    setIsLoading(true);
    requestBackend(studentRequestConfig).then((studentResponse) => {
      const student = studentResponse.data as Student;
      console.log(student);
      
      setStudent(studentResponse.data);
    });
  }, [enrollment]);

  const handleDelete = (studentId: number) => {
    setOpen(false);

    const config: AxiosRequestConfig = {
      method: "DELETE",
      url: `/students/${studentId}`,
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
            <div className="mb-3 d-flex align-items-center justify-content-between">
              <h2 className="card-title m-0">
                {student?.name + " " + student?.lastName}
              </h2>
              <GoBackButton />
            </div>
            <h5 className="card-subtitle mb-2">
              Matrícula: {student?.enrollment}
            </h5>
            <p className="card-text d-flex flex-column flex-sm-row">
              <span className="fw-bold me-sm-1">Data de nascimento:</span>{" "}
              <span>{student ? formatDate(student.birthDate) : <></>}</span>
            </p>
            <p className="card-text">
              <span className="fw-bold">CPF:</span>{" "}
              {student ? formatCpf(student.socialId.toString()) : <></>}
            </p>
            {/*
            <p className="card-text">
              <span className="fw-bold">Turma:</span>{" "}
              {schoolClass?.name + " - " + schoolClass?.period}
            </p>
            <p className="card-text">
              <span className="fw-bold">Responsável:</span>{" "}
              {parent?.name + " " + parent?.lastName} &#47;
              <span className="fw-bold"> Telefone:</span>{" "}
              {parent ? formatPhoneNumber(parent.phone) : <></>}
            </p>
          */}
            <p className="card-text">
              <span className="fw-bold">Endereço:</span> {student?.address.zipCode},{" "}
              {student?.address.complement}, nº {student?.address.number}, CEP:{" "}
              {student ? formatCep(student.address.zipCode.toString()) : <></>}, Bairro:{" "}
              {student?.address.district}, {student?.address.city.name} - {student?.address.city.state.name}
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
