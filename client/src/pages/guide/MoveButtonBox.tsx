import Button from '@/components/Button';
import { Link } from 'react-router-dom';
import { PathType } from './type';
import { useTagSelectContext } from '@/store/useTagSelectContext';

interface MoveButtonProps {
  path: Exclude<PathType, 'complete'>;
}

function MoveButtonBox({ path }: MoveButtonProps) {
  const { selectedAge, selectedGender, selectedKeyword } =
    useTagSelectContext();
  return (
    <>
      {path === 'age' && (
        <>
          <Link to="/model/LX06/guide/gender">
            <Button size="lg" color="main-blue" disabled={selectedAge === null}>
              다음
            </Button>
          </Link>
        </>
      )}
      {path === 'gender' && (
        <>
          <Link to="/model/LX06/guide/age">
            <Button size="lg" color="grey">
              이전
            </Button>
          </Link>
          <Link to="/model/LX06/guide/keyword">
            <Button
              size="lg"
              color="main-blue"
              disabled={selectedGender === null}
            >
              다음
            </Button>
          </Link>
        </>
      )}
      {path === 'keyword' && (
        <>
          <Link to="/model/LX06/guide/gender">
            <Button size="lg" color="grey">
              이전
            </Button>
          </Link>
          <Link to="/model/LX06/guide/complete">
            <Button
              size="lg"
              color="main-blue"
              disabled={selectedKeyword.length !== 3}
            >
              선택 완료
            </Button>
          </Link>
        </>
      )}
    </>
  );
}

export default MoveButtonBox;
