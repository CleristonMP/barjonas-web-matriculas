import { AxiosRequestConfig } from "axios";
import AppModal from "components/AppModal";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { SchoolClass } from "types/schoolClass";
import { requestBackend } from "util/requests";
import { useState } from "react";

import "./styles.css";

type Props = {
  schollClass: SchoolClass;
  onDelete: Function;
};

const SchoolClassesCrudCard = ({ schollClass, onDelete }: Props) => {
  const [open, setOpen] = useState(false);

  const handleDelete = (schoolClassId: number) => {
    setOpen(false);

    const config: AxiosRequestConfig = {
      method: "DELETE",
      url: `/school-classes/${schoolClassId}`,
      withCredentials: true,
    };

    requestBackend(config)
      .then(() => {
        onDelete();
        toast.info(`A turma ${schollClass.name} foi excluída.`);
      })
      .catch(() => {
        toast.error('Há alunos cadastrados nessa turma.');
      });
  };

  return (
    <div className="card-body flex-sm-column">
      <Link to={schollClass.id!.toString()}>
        <h5 className="card-title">{schollClass.name}</h5>
        <h6 className="card-subtitle mb-3 text-muted">{`Período: ${schollClass.period}`}</h6>
      </Link>
      <div className="d-flex justify-content-center justify-content-sm-between justify-content-md-center">
        <button
          className="btn btn-outline-danger me-4 me-sm-0 me-md-4"
          onClick={() => setOpen(true)}
        >
          EXCLUIR
        </button>
        <Link to={`${schollClass.id}/form`}>
          <button className="btn btn-outline-secondary">EDITAR</button>
        </Link>
      </div>
      <AppModal
        open={open}
        onClose={() => setOpen(false)}
        text="Tem certeza de que deseja excluir esta turma?"
        onConfirmation={() => handleDelete(schollClass.id!)}
      />
    </div>
  );
};

export default SchoolClassesCrudCard;
