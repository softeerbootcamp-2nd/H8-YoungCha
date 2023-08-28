import { hydrogenElectricCars } from '@/assets/mock/ModelListMock';
import {
  ModelChangePopUpText,
  SpecificModelChangePopUpTitle,
} from '../../PopUp/constant';
import PopUp from '../../PopUp';

interface SpecificModelChangePopUpProps {
  activeIndex: number;
  onClick: () => void;
  onClose: () => void;
}

function SpecificModelChangePopUp({
  activeIndex,
  onClick,
  onClose,
}: SpecificModelChangePopUpProps) {
  return (
    <PopUp>
      <PopUp.PopUpMain
        title={`[${hydrogenElectricCars[activeIndex].name}]${SpecificModelChangePopUpTitle.title}`}
        imgSrc={<ModelChangePopUpText.ImgSrc />}
      />
      <PopUp.CenteredDescription
        description={ModelChangePopUpText.description}
      />
      <PopUp.PopUpButton
        greyButtonContent={ModelChangePopUpText.greyButtonContent}
        blueButtonContent={ModelChangePopUpText.blueButtonContent}
        onClick={onClick}
        onClose={onClose}
      />
    </PopUp>
  );
}

export default SpecificModelChangePopUp;
