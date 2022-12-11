import { useParams } from "react-router-dom";
import { SchoolClass } from "types/schoolClass";
import { useEffect, useRef, useState } from "react";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { formatCpf, formatDate } from "util/formatters";
import { Link } from "react-router-dom";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";
import PrintSchoolClassButton from "components/PrintSchoolClassButton";

import "./styles.css";

type UrlParams = {
  schoolClassId: string;
};

const Table = () => {
  const { schoolClassId } = useParams<UrlParams>();
  const [schoolClass, setSchoolClass] = useState<SchoolClass>();
  const [isLoading, setIsLoading] = useState(false);

  const tableRef = useRef(null);

  useEffect(() => {
    const config: AxiosRequestConfig = {
      url: `/school-classes/${schoolClassId}`,
      withCredentials: true,
    };

    setIsLoading(true);
    requestBackend(config)
      .then((response) => {
        setSchoolClass(response.data);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [schoolClassId]);

  return (
    <div className="mb-4 p-2 p-sm-3">
      {isLoading ? (
        <AppLoader />
      ) : (
        <>
          <div className="d-flex align-items-center">
            <GoBackButton />
            <PrintSchoolClassButton
              componentToPrint={tableRef}
              schoolClass={schoolClass}
            />
          </div>
          <table ref={tableRef} className="table table-striped table-font">
            <caption className="h2 tb-caption">{`Turma: ${schoolClass?.name} - ${schoolClass?.period}`}</caption>
            <thead>
              <tr>
                <th scope="col">Mat.</th>
                <th scope="col">Nome</th>
                <th scope="col" className="text-break">
                  Sobrenome
                </th>
                <th scope="col" className="d-none d-sm-table-cell d-print-none">
                  CPF
                </th>
                <th scope="col" className="text-break d-none d-md-table-cell d-print-none">
                  Data de nascimento
                </th>
              </tr>
            </thead>
            <tbody>
              {schoolClass?.students.map((std) => (
                <tr key={std.enrollment}>
                  <th scope="row">{std.enrollment}</th>
                  <td>
                    {" "}
                    <Link to={`/admin/students/${std.enrollment}`}> {std.name} </Link>
                  </td>
                  <td>{std.lastName}</td>
                  <td className="d-none d-sm-table-cell d-print-none">
                    {formatCpf(std.socialId.toString())}
                  </td>
                  <td className="d-none d-md-table-cell d-print-none">
                    {formatDate(std.birthDate)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}
    </div>
  );
};

export default Table;
