import PopUp from '.';
import CenteredDescription from './Contents/CenteredDescription';
import ModelCarousel from './Contents/ModelCarousel';
import PopUpProgressBar from './Contents/PopUpProgressBar';
import { ModelChangePopUp, ModelListPopUpText } from './constant';
import { useModal } from '@/hooks/useModal';

const { isOpen, closePopUp } = useModal();
function ModelListPopUp() {
  return (
    <>
      {isOpen && (
        <PopUp onClose={closePopUp}>
          <PopUp.PopUpMain
            title={ModelChangePopUp.title}
            imgSrc={<ModelChangePopUp.ImgSrc />}
          />
          <CenteredDescription description={ModelListPopUpText.description} />
          <PopUpProgressBar />

          <ModelCarousel />
          <PopUp.PopUpButton
            greyButtonContent={ModelListPopUpText.greyButtonContent}
            blueButtonContent={ModelListPopUpText.blueButtonContent}
          />
        </PopUp>
      )}
    </>
  );
}

export default ModelListPopUp;
