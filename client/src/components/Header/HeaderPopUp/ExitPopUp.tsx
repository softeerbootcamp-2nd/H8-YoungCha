import PopUp from '../../PopUp';
import { ExitPopUpText } from '../../PopUp/constant';
import { useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { UserSelectedOptionDataContext } from '@/store/useUserSelectedOptionContext';

interface ExitPopUpProps {
  closePopUp: () => void;
}

function ExitPopUp({ closePopUp }: ExitPopUpProps) {
  const navigate = useNavigate();
  const { initData } = useContext(UserSelectedOptionDataContext);
  function handleOnClick() {
    closePopUp();
    initData();
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
