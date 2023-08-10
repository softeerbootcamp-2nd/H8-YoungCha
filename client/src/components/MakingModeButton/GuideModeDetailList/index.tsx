import { GUIDE_MODE_DETAIL } from '@/components/MakingModeButton/GuideModeDetailList/constant';

function GuideModeDetailList() {
  return (
    <ul className="flex flex-col gap-20px ">
      {GUIDE_MODE_DETAIL.map(({ title, detail }, index) => {
        return (
          <li key={`GuideModeDetail-${index}`} className="flex gap-12px">
            <div className="text-center rounded-full bg-main-blue w-20px h-20px body3">
              {index + 1}
            </div>
            <div>
              <div className="font-medium whitespace-pre-line title4">
                {title}
              </div>
              <div className="whitespace-pre-line body3">{detail}</div>
            </div>
          </li>
        );
      })}
    </ul>
  );
}

export default GuideModeDetailList;
