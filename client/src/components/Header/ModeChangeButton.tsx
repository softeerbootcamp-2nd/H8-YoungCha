import { useContext } from 'react';
import * as Icon from '../../assets/icons';
import { ModeContext } from '@/App';
import { useModal } from '@/hooks/useModal';
import ModeChangePopUp from './HeaderPopUp/ModeChangePopUp';

const MODE_TEXT = {
  self: '셀프 모드',
  guide: '가이드 모드',
  none: '',
};

function ModeChangeButton() {
  const mode = useContext(ModeContext);
  const title = MODE_TEXT[mode];

  const { isOpen, openPopUp, closePopUp } = useModal();

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
      {isOpen && <ModeChangePopUp mode={mode} closePopUp={closePopUp} />}
    </>
  );
}

export default ModeChangeButton;
