import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { User } from "types/user";
import { formatRole } from "util/formatters";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { Link } from "react-router-dom";
import { history } from "util/history";
import { toast } from "react-toastify";

import "./styles.css";

type UrlParams = {
  userId: string;
};

const UserDetails = () => {
  const { userId } = useParams<UrlParams>();
  const [user, setUser] = useState<User>();

  useEffect(() => {
    const config: AxiosRequestConfig = {
      url: `/users/${userId}`,
      withCredentials: true,
    };

    requestBackend(config).then((response) => {
      setUser(response.data);
    });
  }, [userId]);

  const handleDelete = (userId: number) => {
    if (!window.confirm("Tem certeza de que deseja excluir este usuário(a)?")) {
      return;
    }

    const config: AxiosRequestConfig = {
      method: "DELETE",
      url: `/users/${userId}`,
      withCredentials: true,
    };

    if (user) {
      requestBackend(config)
        .then(() => {
          toast.info(
            `O(a) usuário(a) ${user.name + " " + user.lastName} foi excluído(a) com sucesso.`
          );
          history.push("/admin/users");
        })
        .catch(() => {
          toast.error("Erro ao excluir usuário(a).");
        });
    }
  };

  return (
    <div className="container mt-3 mb-5 py-lg-3">
      <div className="card base-card user-details-card">
        <div className="card-body">
          <h2 className="card-title">{`${user?.name} ${user?.lastName}`}</h2>
          <h5 className="card-subtitle mb-2 text-muted">{user?.email}</h5>
          <p className="card-text">
            <span className="fw-bold">Funções: </span>
            {user?.roles.map((role) => (
              <span key={role.id}>
                {formatRole(role.authority)}
                {user.roles.length > 1 ? " / " : ""}
              </span>
            ))}
          </p>
          <div className="mt-4 mt-sm-5 d-flex justify-content-between justify-content-sm-around">
            <Link to="form" className="card-link">
              <button
                type="button"
                className="btn btn-outline-secondary custom-btn me-2"
              >
                Editar
              </button>
            </Link>
            <button
              onClick={() => handleDelete(user?.id!)}
              type="button"
              className="btn btn-outline-danger custom-btn"
            >
              Excluir
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserDetails;
