import { useContext } from 'react';
import * as Icon from '../../assets/icons';
import { ModeContext } from '@/App';

const MODE_TEXT = {
  self: '셀프 모드',
  guide: '가이드 모드',
  none: '',
};

function ModeChangeButton() {
  const mode = useContext(ModeContext);
  const title = MODE_TEXT[mode];

  return (
    <button className="flex items-center text-center">
      <span className="">{title}</span>
      <Icon.ModelChangeArrow fill={mode === 'guide' ? '#4CA7CE' : '#202732'} />
    </button>
  );
}

export default ModeChangeButton;
