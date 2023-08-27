import PopUp from '../../PopUp';
import { ExitPopUpText } from '../../PopUp/constant';
import { useNavigate } from 'react-router-dom';

interface ExitPopUpProps {
  closePopUp: () => void;
}

function ExitPopUp({ closePopUp }: ExitPopUpProps) {
  const navigate = useNavigate();

  function handleOnClick() {
    closePopUp();
    sessionStorage.clear();
    navigate('/model/LX06');
  }

  return (
    <PopUp onClose={closePopUp}>
      <PopUp.PopUpMain
        title={ExitPopUpText.title}
        imgSrc={<ExitPopUpText.ImgSrc />}
      />
      <PopUp.CenteredDescription description={ExitPopUpText.description} />
      <PopUp.PopUpButton
        greyButtonContent={ExitPopUpText.greyButtonContent}
        blueButtonContent={ExitPopUpText.blueButtonContent}
        onClose={handleOnClick}
        onClick={closePopUp}
      />
    </PopUp>
  );
}

export default ExitPopUp;
