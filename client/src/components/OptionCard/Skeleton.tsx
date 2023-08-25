import { GreyCheckIcon, MoreDetailsArrow } from '@/assets/icons';

function Skeleton() {
  return (
    <button
      className="relative border-2 rounded-6px min-w-320px w-full p-20px cursor-pointer
      border-grey-003 bg-white max-h-fit transition-all ease-in duration-500 animate-pulse"
    >
      <div className="flex gap-8px">
        <GreyCheckIcon />
      </div>
      <div className="body2 mt-10px mb-4px rounded-6px bg-grey-002 text-grey-002 w-100px">
        {/*<span className="rounded-6px bg-grey-003 h-10px" />*/}
        {'Loading'}
      </div>
      <div className="font-medium text-20px mb-10px bg-grey-002 text-grey-002 w-180px  rounded-6px">
        {/*<span className="rounded-6px bg-grey-003 h-8px" />*/}
        {'Loading'}
      </div>
      <div className="flex justify-between">
        <div className="body2 text-grey-003">+ 00,000,000원</div>
        <div className="relative flex justify-center text-center gap-2px">
          <span className="font-medium text-grey-003 body3">자세히 보기</span>
          <MoreDetailsArrow className="transition duration-300 transform-gpu" />
        </div>
      </div>
    </button>
  );
}

export default Skeleton;
