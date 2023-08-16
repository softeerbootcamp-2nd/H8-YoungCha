import Button from '@/components/Button';
import { TEXT } from './constant';

function CompleteGuide() {
  return (
    <div className="flex flex-col items-center w-full gap-16px">
      <img
        src="/src/assets/mock/images/palisade.png"
        alt="palisade"
        width={400}
        height={300}
      />

      <h1 className="font-hsans-head text-32px font-medium leading-[44.8px] tracking-[-1.28px]">
        {TEXT.TITLE.complete}
      </h1>
      <div className="w-300px h-86px rounded-6px bg-[#F8F8F8] flex flex-col gap-4px justify-center items-center">
        <span className="body2 text-[#737373]">선택된 트림</span>
        <span className="title2 text-[#212121]">
          팰리세이드 Le Blanc (르블랑)
        </span>
      </div>
      <div className="text-[#737373] body1 flex flex-col items-center">
        <span>준비된 견적을 바로 확인하거나,</span>
        <span>옵션을 차례로 살펴보며 변경할 수 있어요.</span>
      </div>
      <div className="flex gap-12px mt-20px w-700px h-55px">
        <Button color="main-blue" size="full">
          완성된 견적을 바로 볼게요
        </Button>
        <Button color="grey" size="full">
          옵션을 하나씩 살펴볼래요
        </Button>
      </div>
    </div>
  );
}

export default CompleteGuide;
