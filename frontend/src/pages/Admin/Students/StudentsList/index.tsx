import { Link } from "react-router-dom";
import { SpringPage } from "types/vendor/spring";
import { Student } from "types/student";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { useEffect, useState, useCallback } from "react";
import StudentFilter, { StudentFilterData } from "../StudentFilter";
import StudentCrudCard from "../StudentCrudCard";
import Pagination from "components/Pagination";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";

type ControlComponentsData = {
  activePage: number;
  filterData?: StudentFilterData;
};

const StudentsList = () => {
  const [page, setPage] = useState<SpringPage<Student>>();
  const [isLoading, setIsLoading] = useState(false);

  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({
      activePage: 0,
      filterData: { name: "", schoolClass: null },
    });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
      filterData: controlComponentsData.filterData,
    });
  };

  const handleSubmitFilter = (data: StudentFilterData) => {
    setControlComponentsData({ activePage: 0, filterData: data });
  };

  const getStudents = useCallback(() => {
    const config: AxiosRequestConfig = {
      url: "/students",
      withCredentials: true,
      params: {
        size: 12,
        page: controlComponentsData.activePage,
        name: controlComponentsData.filterData?.name,
        schoolClassId: controlComponentsData.filterData?.schoolClass?.id,
      },
    };

    setIsLoading(true);
    requestBackend(config)
      .then((response) => {
        setPage(response.data);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [
    controlComponentsData.activePage,
    controlComponentsData.filterData?.schoolClass?.id,
    controlComponentsData.filterData?.name,
  ]);

  useEffect(() => {
    getStudents();
  }, [getStudents]);

  return (
    <div className="container mb-2 mb-xl-0 py-lg-3 pb-xl-0">
      <div className="container mb-3 text-center px-xl-5 text-lg-start">
        <GoBackButton />
        <Link to="create/form">
          <button className="btn btn-primary text-white h-50px btn-crud-add">
            ADICIONAR
          </button>
        </Link>
        <StudentFilter onSubmitFilter={handleSubmitFilter} />
      </div>
      <div className="container">
        <div className="row justify-content-between px-xl-5">
          {isLoading ? (
            <AppLoader />
          ) : (
            page?.content.map((std) => (
              <div
                className="card base-card std-card mb-3 mb-xl-5 col-sm-6 col-xl-4"
                key={std.enrollment}
              >
                <StudentCrudCard onDelete={getStudents} student={std} />
              </div>
            ))
          )}
        </div>
      </div>

      <div className="row">
        <Pagination
          forcePage={page?.number}
          pageCount={page ? page.totalPages : 0}
          range={3}
          onChange={handlePageChange}
        />
      </div>
    </div>
  );
};

export default StudentsList;
