import PopUp from '@/components/PopUp';
import { ChangeToGasolinePopUp } from '@/components/PopUp/constant';

interface PowerTrainChangePopUpProps {
  closePopUp: () => void;
  setSelectedItem: React.Dispatch<React.SetStateAction<number>>;
}

function PowerTrainChangePopUp({
  closePopUp,
  setSelectedItem,
}: PowerTrainChangePopUpProps) {
  return (
    <PopUp onClose={closePopUp}>
      <PopUp.PopUpMain
        title={ChangeToGasolinePopUp.title}
        imgSrc={<ChangeToGasolinePopUp.ImgSrc />}
      />
      <PopUp.CenteredDescription
        description={ChangeToGasolinePopUp.description}
      />
      <PopUp.DualMufflerImg />
      <PopUp.PopUpButton
        greyButtonContent={ChangeToGasolinePopUp.greyButtonContent}
        blueButtonContent={ChangeToGasolinePopUp.blueButtonContent}
        onClick={closePopUp}
        onClose={() => {
          closePopUp();
          setSelectedItem(0);
        }}
      />
    </PopUp>
  );
}

export default PowerTrainChangePopUp;
