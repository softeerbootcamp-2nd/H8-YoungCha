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
          {selectedAge !== null ? (
            <Link to="/model/LX06/guide/gender">
              <Button size="lg" color="main-blue">
                다음
              </Button>
            </Link>
          ) : (
            <Button size="lg" color="sub-blue" disabled={true}>
              다음
            </Button>
          )}
        </>
      )}
      {path === 'gender' && (
        <>
          <Link to="/model/LX06/guide/age">
            <Button size="lg" color="grey">
              이전
            </Button>
          </Link>
          {selectedGender !== null ? (
            <Link to="/model/LX06/guide/keyword">
              <Button size="lg" color="main-blue">
                다음
              </Button>
            </Link>
          ) : (
            <Button size="lg" color="sub-blue" disabled={true}>
              다음
            </Button>
          )}
        </>
      )}
      {path === 'keyword' && (
        <>
          <Link to="/model/LX06/guide/gender">
            <Button size="lg" color="grey">
              이전
            </Button>
          </Link>
          {selectedKeyword.length === 3 ? (
            <Link to="/model/LX06/guide/complete">
              <Button size="lg" color="main-blue">
                선택 완료
              </Button>
            </Link>
          ) : (
            <Button size="lg" color="sub-blue" disabled={true}>
              선택 완료
            </Button>
          )}
        </>
      )}
    </>
  );
}

export default MoveButtonBox;
