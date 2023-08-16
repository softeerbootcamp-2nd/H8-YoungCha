import Button from '@/components/Button';
import OptionLayout from './OptionLayout';
import { Link } from 'react-router-dom';
import { TITLE } from './constant';

function MakingCarButtonsBox() {
  return (
    <OptionLayout>
      {Array.from({ length: 4 }).map((_, index) => (
        <Link to="/model/LX06/making/self/1" key={index}>
          <Button size="md" color="main-blue">
            {TITLE.MAKING_MY_CAR}
          </Button>
        </Link>
      ))}
    </OptionLayout>
  );
}

export default MakingCarButtonsBox;
