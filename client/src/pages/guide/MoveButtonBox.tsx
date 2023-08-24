import Button from '@/components/Button';
import { Link } from 'react-router-dom';
import { PathType } from './type';
import { useTagSelectContext } from '@/store/useTagSelectContext';
import { keywordId } from './constant';
import { get } from '@/service';
import { AllGuideOptionType } from '@/types/option';

interface MoveButtonProps {
  path: Exclude<PathType, 'complete'>;
}

function MoveButtonBox({ path }: MoveButtonProps) {
  const trimId = 2;
  const { selectedAge, selectedGender, selectedKeyword } =
    useTagSelectContext();

  async function handleClick() {
    const [age, gender, keyword1Id, keyword2Id, keyword3Id] = [
      keywordId[selectedAge!],
      keywordId[selectedGender!],
      keywordId[selectedKeyword[0] as keyof typeof keywordId],
      keywordId[selectedKeyword[1] as keyof typeof keywordId],
      keywordId[selectedKeyword[2] as keyof typeof keywordId],
    ];
    const exterirColorData = await get<AllGuideOptionType[]>({
      url: `/car-make/${trimId}/guide/exterior-color`,
      params: { age, gender, keyword1Id, keyword2Id, keyword3Id },
    });
    const exteriorColorId = String(
      exterirColorData?.filter((data) => data.checked)[0].id
    );
    const params = {
      age,
      gender,
      keyword1Id,
      keyword2Id,
      keyword3Id,
      exteriorColorId,
    };
    const urls = [
      {
        url: `/car-make/${trimId}/guide/wheel`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/power-train`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/options`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/interior-color`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/driving-system`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/body-type`,
        params,
      },
    ];

    const options = await Promise.all(
      urls.map(({ url, params }) => get<AllGuideOptionType>({ url, params }))
    );
    console.log(options);
  }

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
              onClick={handleClick}
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
