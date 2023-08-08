import HyundaiLogoButton from './HyundaiLogoButton';
import DictionaryToggleButton from './DictionaryToggleButton';
import ModelChangeButton from './ModelChangeButton';

interface HeaderProps {
  backgroundColor?: string;
}

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
          <span className="pl-20px title3 font-medium">내 차 만들기</span>
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
