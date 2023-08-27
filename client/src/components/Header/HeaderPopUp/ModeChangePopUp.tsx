import { useState } from 'react';
import PopUp from '../../PopUp';
import {
  ChangeToGuideModePopUp,
  ChangeToSelfModePopUp,
  GuideModeCard,
  ModeChangePopUpText,
  SelfModeCard,
} from '../../PopUp/constant';
import { useLocation, useNavigate } from 'react-router-dom';
import { ModeType } from '@/types';

interface ModeChangePopUpProps {
  mode: ModeType;
  closePopUp: () => void;
}

function ModeChangePopUp({ mode, closePopUp }: ModeChangePopUpProps) {
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);

  const guidePathname = '/model/LX06/guide/age';
  const selfPathname = '/model/LX06/making/self/1';

  const changeToNextModePopUp =
    mode === 'self' ? ChangeToGuideModePopUp : ChangeToSelfModePopUp;
  const isGuide = pathname.includes('guide');

  return (
    <PopUp onClose={closePopUp}>
      {isConfirmOpen ? (
        <>
          <PopUp.PopUpMain
            title={changeToNextModePopUp.title}
            imgSrc={<changeToNextModePopUp.ImgSrc />}
          />
          <PopUp.CenteredDescription
            description={changeToNextModePopUp.description}
          />
          <PopUp.PopUpButton
            greyButtonContent={changeToNextModePopUp.greyButtonContent}
            blueButtonContent={changeToNextModePopUp.blueButtonContent}
            onClose={closePopUp}
            onClick={() => {
              closePopUp();
              sessionStorage.clear();
              navigate(isGuide ? selfPathname : guidePathname);
            }}
          />
        </>
      ) : (
        <>
          <PopUp.PopUpMain
            title={ModeChangePopUpText.title}
            imgSrc={<ModeChangePopUpText.ImgSrc />}
          />

          <div className={`${mode !== 'self' ? 'cursor-pointer' : ''}`}>
            <PopUp.ModeSelectCard
              currentMode={mode}
              mode={SelfModeCard.mode}
              description={SelfModeCard.description}
              onClick={() => setIsConfirmOpen(true)}
            />
          </div>

          <div className={`${mode !== 'guide' ? 'cursor-pointer' : ''}`}>
            <PopUp.ModeSelectCard
              currentMode={mode}
              mode={GuideModeCard.mode}
              description={GuideModeCard.description}
              onClick={() => setIsConfirmOpen(true)}
            />
          </div>
        </>
      )}
    </PopUp>
  );
}

export default ModeChangePopUp;
