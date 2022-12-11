import { ReactComponent as CloseIcon } from "assets/images/close-icon.svg";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { useState } from "react";
import { toast } from "react-toastify";
import {
  Modal,
  Header,
  Button,
  Icon,
  ModalProps,
  ButtonProps,
} from "semantic-ui-react";

import "./styles.css";

type AddCounty = {
  name: string;
  state: string;
};

type Props = {
  open: boolean;
  setOpen: Function;
  updateCounties: Function;
  onClose: (
    event: React.MouseEvent<HTMLElement, MouseEvent>,
    data: ModalProps | ButtonProps
  ) => void;
};

const AddCityModal = ({ open, setOpen, updateCounties, onClose }: Props) => {
  const [countyName, setCountyName] = useState<string>();

  const handleCountySubmit = () => {
    if (countyName && countyName.length >= 3) {
      const data: AddCounty = {
        name: countyName,
        state: "MA",
      };

      const config: AxiosRequestConfig = {
        method: "POST",
        url: "/cities",
        data,
        withCredentials: true,
      };

      requestBackend(config)
        .then(() => {
          toast.info("Município cadastrado com sucesso.");
        })
        .catch(() => {
          toast.error("Erro ao cadastrar município.");
        })
        .finally(() => {
          if (document.querySelector("#countyName")) {
            // @ts-ignore
            const element: HTMLInputElement =
              document.getElementById("countyName");
            element.value = "";
            updateCounties();
            setOpen(() => (open = false));
          }
        });
    } else {
      toast.error("Erro ao cadastrar município.");
    }
  };

  return (
    <Modal
      open={open}
      onClose={onClose}
      size="tiny"
      dimmer="inverted"
      className="cities-modal"
      closeIcon={<CloseIcon className="close-icon" />}
    >
      <Header content="Adicionar Município" />
      <Modal.Content>
        <div className="container add-county-form">
          <label htmlFor="name">Nome</label>
          <input
            type={"text"}
            className="form-control base-input"
            id="countyName"
            onChange={(event) => setCountyName(event.target.value)}
          />
        </div>
      </Modal.Content>
      <Modal.Actions>
        <Button color="grey" onClick={onClose}>
          <Icon name="cancel" /> Cancelar
        </Button>
        <Button color="blue" onClick={handleCountySubmit}>
          <Icon name="save" /> Salvar
        </Button>
      </Modal.Actions>
    </Modal>
  );
};

export default AddCityModal;
