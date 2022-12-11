import { ReactComponent as SearchIcon } from "assets/images/search_icon.svg";
import { useForm } from "react-hook-form";
import { getEnumKeys } from "util/helpers";
import { Period } from "types/enums/period";

import "./styles.css";

export type SchoolClassFilterData = {
  name?: string;
  period: Period | null;
};

type Props = {
  onSubmitFilter: (data: SchoolClassFilterData) => void;
};

const getPeriodPT_BR = (value: string) => {
  const periods: any = {
    MORNING: "Matutino" ,
    EVENING: "Vespertino",
    NIGHT: "Noturno",
  };
  return periods[value];
};

const SchoolClassFilter = ({ onSubmitFilter }: Props) => {
  const { register, handleSubmit, setValue, getValues } =
    useForm<SchoolClassFilterData>();

  const onSubmit = (formData: SchoolClassFilterData) => {
    onSubmitFilter(formData);
  };

  const handleFormClear = () => {
    setValue("name", "");
    setValue("period", null);
  };

  const handleChangePeriod = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const obj: SchoolClassFilterData = {
      name: getValues("name"),
      period: Period[event.target.value as keyof typeof Period],
    };
    onSubmitFilter(obj);
  };

  return (
    <div className="base-card filter-ctr">
      <form onSubmit={handleSubmit(onSubmit)} className="filter-form">
        <div className="filter-name-ctr">
          <input
            {...register("name")}
            type="text"
            className={"form-control"}
            placeholder="Buscar turma"
            name="name"
          />
          <button className="filter-search-icon">
            <SearchIcon />
          </button>
        </div>
        <div className="filter-bottom-ctr">
          <div className="filter-period-ctr">
            <select
              {...register("period")}
              onChange={handleChangePeriod}
              className="filter-select"
            >
              <option value="" key="" selected>Período</option>
              {getEnumKeys(Period).map((key, index) => (
                <option value={Period[key]} key={index}>
                  {getPeriodPT_BR(key)}
                </option>
              ))}
            </select>
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

export default SchoolClassFilter;
