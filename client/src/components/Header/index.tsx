import HyundaiLogoButton from './HyundaiLogoButton';
import DictionaryToggleButton from './DictionaryToggleButton';
import ModelChangeButton from './ModelChangeButton';

interface HeaderProps {
  backgroundColor?: string;
}

const TEXT_MAKING_MY_CAR = '내 차 만들기';

function Header({ backgroundColor }: HeaderProps) {
  return (
    <div
      className={`flex justify-between max-w-7xl px-128px py-32px fixed z-100 w-full mx-auto left-0 right-0 ${backgroundColor}`}
    >
      <div className="flex text-center">
        <HyundaiLogoButton />
        <div>
          <span>|</span>
        </div>
        <div>
          <span className="font-medium pl-20px title3">
            {TEXT_MAKING_MY_CAR}
          </span>
        </div>
      </div>
      <div className="flex gap-20px">
        <DictionaryToggleButton />
        <div>
          <span>|</span>
        </div>
        <ModelChangeButton />
      </div>
    </div>
  );
}

export default Header;
