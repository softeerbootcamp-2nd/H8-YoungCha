import PopUp from '../../PopUp';
import CenteredDescription from '../../PopUp/Contents/CenteredDescription';
import ModelCarousel from '../../PopUp/Contents/ModelCarousel';
import PopUpProgressBar from '../../PopUp/Contents/PopUpProgressBar';
import { ModelChangePopUpText, ModelListPopUpText } from '../../PopUp/constant';

interface ModelListPopUpProps {
  onClick: () => void;
  onClose: () => void;
}

function ModelListPopUp({ onClick, onClose }: ModelListPopUpProps) {
  return (
    <>
      <PopUp.PopUpMain
        title={ModelChangePopUpText.title}
        imgSrc={<ModelChangePopUpText.ImgSrc />}
      />
      <CenteredDescription description={ModelListPopUpText.description} />
      <PopUpProgressBar />

      <ModelCarousel />
      <PopUp.PopUpButton
        greyButtonContent={ModelListPopUpText.greyButtonContent}
        blueButtonContent={ModelListPopUpText.blueButtonContent}
        onClick={onClick}
        onClose={onClose}
      />
    </>
  );
}

export default ModelListPopUp;
