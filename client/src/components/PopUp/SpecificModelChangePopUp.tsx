import { hydrogenElectricCars } from '@/assets/mock/ModelListMock';
import { ModelChangePopUp, SpecificModelChangePopUpTitle } from './constant';
import PopUp from '.';

interface SpecificModelChangePopUpProps {
  activeIndex: number;
}

function SpecificModelChangePopUp({
  activeIndex,
}: SpecificModelChangePopUpProps) {
  return (
    <PopUp>
      <PopUp.PopUpMain
        title={`[${hydrogenElectricCars[activeIndex].name}]${SpecificModelChangePopUpTitle.title}`}
        imgSrc={<ModelChangePopUp.ImgSrc />}
      />
      <PopUp.CenteredDescription description={ModelChangePopUp.description} />
      <PopUp.PopUpButton
        greyButtonContent={ModelChangePopUp.greyButtonContent}
        blueButtonContent={ModelChangePopUp.blueButtonContent}
      />
    </PopUp>
  );
}

export default SpecificModelChangePopUp;
