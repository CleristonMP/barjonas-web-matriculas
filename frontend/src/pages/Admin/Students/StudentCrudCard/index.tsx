import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { getPeriod_PT_BR } from "util/helpers";
import { formatDate } from "util/formatters";
import { useState } from "react";
import { Student } from "types/student";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";
import AppModal from "components/AppModal";

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
      <Link to={student.enrollment.toString()}>
        <h5 className="card-title mb-sm-3">
          {`${student.name} ${student.lastName}`}
        </h5>
        <h6 className="card-subtitle text-muted mb-sm-2">{`Matrícula: ${student.enrollment}`}</h6>
      </Link>
      <p className="card-text text-start mb-0">{`Data de nascimento: ${formatDate(student.birthDate)}`}</p>
      <p className="card-text text-start mb-0">{`Turma: ${student.schoolClass.name}`}</p>
      <p className="card-text text-start mb-0">{`Período: ${getPeriod_PT_BR(student.schoolClass.period)}`}</p>
      <p className="card-text text-start mb-sm-3 text-capitalize">{`Etapa: ${student.schoolClass.phase.description.toLowerCase()}`}</p>
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
        onConfirmation={() => handleDelete(student.enrollment)}
      />
    </div>
  );
};

export default StudentCrudCard;
