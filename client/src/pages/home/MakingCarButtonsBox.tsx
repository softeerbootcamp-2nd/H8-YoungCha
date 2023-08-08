import OptionLayout from './OptionLayout';

const MAKING_MY_CAR_TITLE = '내 차 만들기';

function MakingCarButtonsBox() {
  return (
    <OptionLayout>
      <li className="flex items-center justify-center text-white w-140px h-50px bg-main-blue rounded-6px">
        {MAKING_MY_CAR_TITLE}
      </li>
      <li className="flex items-center justify-center text-white w-140px h-50px bg-main-blue rounded-6px">
        {MAKING_MY_CAR_TITLE}
      </li>
      <li className="flex items-center justify-center text-white w-140px h-50px bg-main-blue rounded-6px">
        {MAKING_MY_CAR_TITLE}
      </li>
      <li className="flex items-center justify-center text-white w-140px h-50px bg-main-blue rounded-6px">
        {MAKING_MY_CAR_TITLE}
      </li>
    </OptionLayout>
  );
}

export default MakingCarButtonsBox;
