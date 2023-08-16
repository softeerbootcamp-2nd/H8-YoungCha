import ChevronRight from '@/assets/icons/ChevronRight';
import { SELF_MODE, GUIDE_MODE, PROGRESSING_NOW } from '../constant';

interface ModeSelectCardProps {
  currentMode: string;
  mode: string;
  description: string;
}

function ModeSelectCard({
  currentMode,
  mode,
  description,
}: ModeSelectCardProps) {
  const borderColor =
    mode === SELF_MODE
      ? 'border-main-blue mt-20px'
      : 'border-sub-blue bg-sub-blue mt-12px';
  const titleTextColor = mode === SELF_MODE ? '' : 'text-white';
  const tagTextColor = mode === SELF_MODE ? 'opacity-50' : 'text-white';

  return (
    <div
      className={`${borderColor} rounded-6px w-301px h-116px border-1px pt-18px pb-24px pl-18px pr-10px m-4px`}
    >
      <div className="flex">
        <span className={`${titleTextColor} title5 font-medium`}>{mode}</span>
        {mode === SELF_MODE && currentMode === 'self' && (
          <span className="text-main-blue text-10px font-medium rounded-2px bg-tag-skyblue py-2px px-5px ml-5px">
            {PROGRESSING_NOW}
          </span>
        )}
        {mode === GUIDE_MODE && currentMode === 'guide' && (
          <span className="text-main-blue text-10px font-medium rounded-2px bg-tag-skyblue py-2px px-5px ml-5px opacity-80">
            {PROGRESSING_NOW}
          </span>
        )}
      </div>
      <div className="mt-10px flex flex-wrap">
        <div className={`${tagTextColor} body3`}>
          <p className="whitespace-pre-wrap">{description}</p>
        </div>
        <div className="absolute left-285px">
          {mode === SELF_MODE ? (
            <ChevronRight size={24} />
          ) : (
            <ChevronRight size={24} color={'white'} />
          )}
        </div>
      </div>
    </div>
  );
}

export default ModeSelectCard;
