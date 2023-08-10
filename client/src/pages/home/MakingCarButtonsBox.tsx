import Button from '@/components/Button';
import OptionLayout from './OptionLayout';
import { Link } from 'react-router-dom';

const MAKING_MY_CAR_TITLE = '내 차 만들기';

function MakingCarButtonsBox() {
  return (
    <OptionLayout>
      {Array.from({ length: 4 }).map((_, index) => (
        <Link to="/model/LX06/making/self/1" key={index}>
          <Button size="md" color="main-blue">
            {MAKING_MY_CAR_TITLE}
          </Button>
        </Link>
      ))}
    </OptionLayout>
  );
}

export default MakingCarButtonsBox;
