import { useState } from 'react';
import { ModelChangePopUpText } from '../../PopUp/constant';
import PopUp from '../../PopUp';
import ModelListPopUp from './ModelListPopUp';
import SpecificModelChangePopUp from './SpecificModelChangePopUp';

interface ModelChangePopUpProps {
  closePopUp: () => void;
}

function ModelChangePopUp({ closePopUp }: ModelChangePopUpProps) {
  const [isModelListOpen, setIsModelListOpen] = useState(false);
  const [isSpecificModelChangeOpen, setIsSpecificModelChangeOpen] =
    useState(false);

  return (
    <PopUp onClose={closePopUp}>
      {isModelListOpen ? (
        <ModelListPopUp
          onClick={() => setIsSpecificModelChangeOpen(true)}
          onClose={closePopUp}
        />
      ) : (
        <>
          <PopUp.PopUpMain
            title={ModelChangePopUpText.title}
            imgSrc={<ModelChangePopUpText.ImgSrc />}
          />
          <PopUp.CenteredDescription
            description={ModelChangePopUpText.description}
          />
          <PopUp.PopUpButton
            greyButtonContent={ModelChangePopUpText.greyButtonContent}
            blueButtonContent={ModelChangePopUpText.blueButtonContent}
            onClick={closePopUp}
            onClose={() => setIsModelListOpen(true)}
          />
        </>
      )}
      {isSpecificModelChangeOpen && (
        <SpecificModelChangePopUp
          activeIndex={0}
          onClick={() => setIsSpecificModelChangeOpen(false)}
          onClose={closePopUp}
        />
      )}
    </PopUp>
  );
}

export default ModelChangePopUp;
