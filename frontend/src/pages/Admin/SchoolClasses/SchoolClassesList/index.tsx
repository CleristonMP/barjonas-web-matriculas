import SchoolClassesCrudCard from "../SchoolClassesCrudCard";
import { Link } from "react-router-dom";
import { useEffect, useState, useCallback } from "react";
import { requestBackend } from "util/requests";
import { AxiosRequestConfig } from "axios";
import { SchoolClass } from "types/schoolClass";
import { SpringPage } from "types/vendor/spring";
import Pagination from "components/Pagination";
import SchoolClassFilter, { SchoolClassFilterData } from "../SchoolClassFilter";
import GoBackButton from "components/GoBackButton";
import AppLoader from "components/AppLoader";

type ControlComponentsData = {
  activePage: number;
  filterData?: SchoolClassFilterData;
};

const SchoolClassesList = () => {
  const [page, setPage] = useState<SpringPage<SchoolClass>>();
  const [isLoading, setIsLoading] = useState(false);

  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({ activePage: 0 });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
      filterData: controlComponentsData.filterData,
    });
  };

  const handleSubmitFilter = (data: SchoolClassFilterData) => {
    setControlComponentsData({ activePage: 0, filterData: data });
  };

  const getSchoolClasses = useCallback(() => {
    const config: AxiosRequestConfig = {
      url: "/school-classes",
      withCredentials: true,
      params: {
        page: controlComponentsData.activePage,
        size: 6,
        name: controlComponentsData.filterData?.name,
        period: controlComponentsData.filterData?.period,
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
    controlComponentsData.filterData?.name,
    controlComponentsData.filterData?.period,
  ]);

  useEffect(() => {
    getSchoolClasses();
  }, [getSchoolClasses]);

  return (
    <div className="container mb-2 py-lg-3">
      <div className="container mb-3 text-center text-lg-start px-xl-5">
        <GoBackButton />
        <Link to="create/form">
          <button className="btn btn-primary text-white h-50px btn-crud-add">
            ADICIONAR
          </button>
        </Link>
        <SchoolClassFilter onSubmitFilter={handleSubmitFilter} />
      </div>
      <div className="container">
        <div className="row justify-content-sm-between px-xl-5">
          {isLoading ? (
            <AppLoader />
          ) : (
            page?.content.map((sc) => (
              <div
                className="card base-card text-center school-class-card mb-3 col-sm-6 col-lg-4"
                key={sc.id}
              >
                <SchoolClassesCrudCard
                  schollClass={sc}
                  onDelete={getSchoolClasses}
                />
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

export default SchoolClassesList;
