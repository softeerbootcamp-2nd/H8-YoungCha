import { useState } from 'react';
import PopUp from '../PopUp';
import {
  ChangeToGuideModePopUp,
  ChangeToSelfModePopUp,
  GuideModeCard,
  ModeChangePopUp,
  SelfModeCard,
} from '../PopUp/constant';
import { useLocation, useNavigate } from 'react-router-dom';
import { ModeType } from '@/types';

interface ModeChangeModalProps {
  mode: ModeType;
  closePopUp: () => void;
}

function ModeChangeModal({ mode, closePopUp }: ModeChangeModalProps) {
  const [isConfirmOepn, setIsConfirmOpen] = useState(false);

  const { pathname } = useLocation();
  const navigate = useNavigate();
  const guidePathname = pathname.replace('self', 'guide');
  const selfPathname = pathname.replace('guide', 'self');

  const changeToNextModePopUp =
    mode === 'self' ? ChangeToGuideModePopUp : ChangeToSelfModePopUp;
  return (
    <PopUp onClose={closePopUp}>
      {isConfirmOepn ? (
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
              navigate(mode === 'self' ? guidePathname : selfPathname);
            }}
          />
        </>
      ) : (
        <>
          <PopUp.PopUpMain
            title={ModeChangePopUp.title}
            imgSrc={<ModeChangePopUp.ImgSrc />}
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

export default ModeChangeModal;