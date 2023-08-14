import Button from '@/components/Button';
import ProgressBar from '@/components/ProgressBar';
import { Link, useParams } from 'react-router-dom';

interface MakingProps {
  path: 'self' | 'guide';
}

const TITLE = [
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '외장 색상',
  '내장 색상',
  '휠',
  '옵션',
];

const PULL_IMAGE_PAGE = [1, 2, 4];

function Making({ path }: MakingProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  const paddingSize = PULL_IMAGE_PAGE.includes(currentStep)
    ? 'px-128px'
    : 'pr-128px';

  return (
    <div className="flex flex-col w-full h-screen pt-85px">
      <ProgressBar step={currentStep} path={path} id={id} />
      <div
        className={`m-auto max-w-7xl ${paddingSize} flex justify-between items-center w-full flex-1`}
      >
        <div className="flex items-center justify-center ">
          <img src="/src/assets/mock/images/palisade.png" alt="palisade" />
        </div>
        <div className="flex flex-col justify-center h-full w-375px gap-30px">
          <div className="flex gap-12px">
            <h1 className="font-hsans-head text-24px tracking-[-0.96px]">
              <strong className="font-medium">{TITLE[currentStep - 1]}</strong>
              을 선택해주세요.
            </h1>
          </div>
          <div>
            <div className="flex flex-col items-center gap-12px h-400px">
              <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
                디젤 2.2 box
              </div>
              <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
                디젤 2.2 box
              </div>
            </div>

            <div className="flex justify-between">
              <div className="flex flex-col gap-5px">
                <div className="flex gap-5px">
                  <span className="body2 text-grey-003">총 견적금액</span>
                  <button>^</button>
                </div>
                <span className="title1 text-grey-black">47,200,000원</span>
              </div>
              <div className="flex items-center gap-21px">
                {currentStep !== 1 && (
                  <Link
                    to={`/model/${id}/making/${path}/${Number(step) - 1}`}
                    className="body2 text-grey-003 "
                  >
                    이전
                  </Link>
                )}
                <Link to={`/model/${id}/making/${path}/${Number(step) + 1}`}>
                  <Button size="sm">선택 완료</Button>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Making;
