import Button from '@/components/Button';
import OptionLayout from './OptionLayout';
import { Link } from 'react-router-dom';
import { TITLE } from './constant';

function MakingCarButtonsBox() {
  return (
    <OptionLayout>
      {Array.from({ length: 4 }).map((_, index) => {
        const to = index === 1 ? 'making/self/1' : '';

        return (
          <Link to={to} key={index}>
            <Button
              size="md"
              color="main-blue"
              style={{ cursor: index !== 1 ? 'not-allowed' : 'pointer' }}
            >
              {TITLE.MAKING_MY_CAR}
            </Button>
          </Link>
        );
      })}
    </OptionLayout>
  );
}

export default MakingCarButtonsBox;
