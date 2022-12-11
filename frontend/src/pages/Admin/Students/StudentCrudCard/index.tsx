import { formatCpf, formatDate } from "util/formatters";
import { requestBackend } from "util/requests";
import { AxiosRequestConfig } from "axios";
import AppModal from "components/AppModal";
import { Student } from "types/student";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { useState } from "react";

import "./styles.css";

type Props = {
  student: Student;
  onDelete: Function;
};

const StudentCrudCard = ({ student, onDelete }: Props) => {
  const [open, setOpen] = useState(false);

  const handleDelete = (studentId: number) => {
    setOpen(false);

    const config: AxiosRequestConfig = {
      method: "DELETE",
      url: `/students/${studentId}`,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        onDelete();
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
  };

  return (
    <div className="card-body custom-card-body flex-sm-column text-sm-center">
      <Link to={student.enrollment!.toString()}>
        <h5 className="card-title mb-sm-3">
          {`${student.name} ${student.lastName}`}
        </h5>
        <h6 className="card-subtitle text-muted mb-sm-2">{`CPF: ${formatCpf(student.socialId.toString())}`}</h6>
      </Link>
      <p className="card-text mb-sm-3">{`Data de nascimento: ${formatDate(student.birthDate)}`}</p>
      <div className="d-flex justify-content-center">
        <button 
          className="btn btn-outline-danger me-4"
          onClick={() => setOpen(true)}
          >EXCLUIR</button>
        <Link to={`${student.enrollment}/form`}>
          <button className="btn btn-outline-secondary">EDITAR</button>
        </Link>
      </div>
      <AppModal
        open={open}
        onClose={() => setOpen(false)}
        text="Tem certeza de que deseja excluir este aluno(a)?"
        onConfirmation={() => handleDelete(student.enrollment!)}
      />
    </div>
  );
};

export default StudentCrudCard;
