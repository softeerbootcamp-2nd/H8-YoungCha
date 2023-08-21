import { useContext } from 'react';
import * as Icon from '../../assets/icons';
import { ModeContext } from '@/App';
import { useModal } from '@/hooks/useModal';
import PopUp from '../PopUp';
import {
  GuideModeCard,
  ModeChangePopUp,
  SelfModeCard,
} from '../PopUp/constant';
import { Link, useLocation } from 'react-router-dom';
import { logger } from '@/utils';

const MODE_TEXT = {
  self: '셀프 모드',
  guide: '가이드 모드',
  none: '',
};

function ModeChangeButton() {
  const mode = useContext(ModeContext);
  const title = MODE_TEXT[mode];

  const { isOpen, openPopUp, closePopUp } = useModal();
  const { pathname } = useLocation();

  const guidePathname = pathname.replace('self', 'guide');
  const selfPathname = pathname.replace('guide', 'self');

  logger(guidePathname, selfPathname);
  return (
    <>
      <button className="flex items-center text-center" onClick={openPopUp}>
        <span className={`${mode === 'guide' ? 'text-sub-blue' : ''}`}>
          {title}
        </span>
        <Icon.ModelChangeArrow
          fill={mode === 'guide' ? '#4CA7CE' : '#202732'}
        />
      </button>
      {isOpen && (
        <PopUp onClose={closePopUp}>
          <PopUp.PopUpMain
            title={ModeChangePopUp.title}
            imgSrc={<ModeChangePopUp.ImgSrc />}
          />

          <Link to={selfPathname}>
            <PopUp.ModeSelectCard
              currentMode={mode}
              mode={SelfModeCard.mode}
              description={SelfModeCard.description}
              onClick={closePopUp}
            />
          </Link>

          <Link to={guidePathname}>
            <PopUp.ModeSelectCard
              currentMode={mode}
              mode={GuideModeCard.mode}
              description={GuideModeCard.description}
              onClick={closePopUp}
            />
          </Link>
        </PopUp>
      )}
    </>
  );
}

export default ModeChangeButton;
