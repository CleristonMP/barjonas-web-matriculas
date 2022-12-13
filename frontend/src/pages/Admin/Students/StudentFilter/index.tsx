import { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { SchoolClass } from "types/schoolClass";
import { requestBackend } from "util/requests";
import { ReactComponent as SearchIcon } from "assets/images/search_icon.svg";
import Select from "react-select";

import "./styles.css";
import { getPeriod_PT_BR } from "util/helpers";

export type StudentFilterData = {
  name?: string;
  schoolClass: SchoolClass | null;
};

type Props = {
  onSubmitFilter: (data: StudentFilterData) => void;
};

const StudentFilter = ({ onSubmitFilter }: Props) => {
  const [schoolClasses, setSchoolClasses] = useState<SchoolClass[]>([]);

  const { register, handleSubmit, setValue, getValues, control } =
    useForm<StudentFilterData>();

  const onSubmit = (formData: StudentFilterData) => {
    onSubmitFilter(formData);
  };

  const handleFormClear = () => {
    setValue("name", "");
    setValue("schoolClass", null);
  };

  const handleChangeSchoolClass = (value: SchoolClass) => {
    setValue("schoolClass", { ...value });

    const obj: StudentFilterData = {
      name: getValues("name"),
      schoolClass: getValues("schoolClass"),
    };

    onSubmitFilter(obj);
  };

  useEffect(() => {
    requestBackend({ url: "/school-classes", withCredentials: true }).then(
      (response) => {
        setSchoolClasses(response.data.content);
      }
    );
  }, []);

  return (
    <div className="base-card filter-ctr">
      <form onSubmit={handleSubmit(onSubmit)} className="filter-form">
        <div className="filter-name-ctr">
          <input
            {...register("name")}
            type="text"
            className={"form-control"}
            placeholder="Buscar aluno(a)"
            name="name"
          />
          <button className="filter-search-icon">
            <SearchIcon />
          </button>
        </div>
        <div className="filter-bottom-ctr">
          <div className="filter-schoolclass-ctr">
            <Controller
              name="schoolClass"
              control={control}
              render={({ field }) => (
                <Select
                  {...field}
                  options={schoolClasses}
                  isClearable
                  placeholder="Turma"
                  classNamePrefix="filter-select"
                  onChange={(value) =>
                    handleChangeSchoolClass(value as SchoolClass)
                  }
                  getOptionLabel={(sc: SchoolClass) => sc.name + " - " + getPeriod_PT_BR(sc.period)}
                  getOptionValue={(sc: SchoolClass) => String(sc.id)}
                />
              )}
            />
          </div>
          <button
            onClick={handleFormClear}
            className="btn btn-outline-secondary btn-filter-clear"
          >
            LIMPAR<span className="btn-filter-word"> FILTRO</span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default StudentFilter;
