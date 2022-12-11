import { ReactComponent as CloseIcon } from "assets/images/close-icon.svg";
import { AxiosRequestConfig } from "axios";
import { requestBackend } from "util/requests";
import { useState } from "react";
import { toast } from "react-toastify";
import { Phase } from "types/phase";
import {
  Modal,
  Header,
  Button,
  Icon,
  ModalProps,
  ButtonProps,
} from "semantic-ui-react";

import "./styles.css";

type Props = {
  open: boolean;
  setOpen: Function;
  updatePhases: Function;
  onClose: (
    event: React.MouseEvent<HTMLElement, MouseEvent>,
    data: ModalProps | ButtonProps
  ) => void;
};

const AddPhaseModal = ({ open, setOpen, updatePhases, onClose }: Props) => {
  const [phaseDescription, setPhaseDescription] = useState<Phase["description"]>();

  const handlePhaseSubmit = () => {
    if (phaseDescription && phaseDescription.length >= 3) {
      const data: Phase = {
        description: phaseDescription,
      };

      const config: AxiosRequestConfig = {
        method: "POST",
        url: "/phases",
        data,
        withCredentials: true,
      };

      requestBackend(config)
        .then(() => {
          toast.info("Etapa cadastrada com sucesso.");
        })
        .catch(() => {
          toast.error("Erro ao cadastrar etapa.");
        })
        .finally(() => {
          if (document.querySelector("#phaseDescription")) {
            // @ts-ignore
            const element: HTMLInputElement =
              document.getElementById("phaseDescription");
            element.value = "";
            updatePhases();
            setOpen(() => (open = false));
          }
        });
    } else {
      toast.error("Erro ao cadastrar etapa.");
    }
  };

  return (
    <Modal
      open={open}
      onClose={onClose}
      size="tiny"
      dimmer="inverted"
      className="phase-modal"
      closeIcon={<CloseIcon className="close-icon" />}
    >
      <Header content="Adicionar Etapa" />
      <Modal.Content>
        <div className="container add-phase-form">
          <label htmlFor="phaseDescription">Etapa</label>
          <input
            type={"text"}
            className="form-control base-input"
            id="phaseDescription"
            onChange={(event) => setPhaseDescription(event.target.value)}
          />
        </div>
      </Modal.Content>
      <Modal.Actions>
        <Button color="grey" onClick={onClose}>
          <Icon name="cancel" /> Cancelar
        </Button>
        <Button color="blue" onClick={handlePhaseSubmit}>
          <Icon name="save" /> Salvar
        </Button>
      </Modal.Actions>
    </Modal>
  );
};

export default AddPhaseModal;
