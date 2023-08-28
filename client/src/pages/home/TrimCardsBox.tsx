import MakingModeButton from '@/components/MakingModeButton';
import removeBracket from '../../utils/removeBracket';
import { GuideType, TrimType } from '@/types';

interface TrimCardsBoxProps {
  trims: TrimType[];
  guide: GuideType;
  setBackgroundImgUrl: React.Dispatch<React.SetStateAction<string>>;
}

function TrimCardsBox({
  trims,
  guide,
  setBackgroundImgUrl,
}: TrimCardsBoxProps) {
  return (
    <div className="flex justify-between gap-16px">
      <MakingModeButton
        name="Guide Mode"
        hashTag={guide.hashTag}
        price={guide.price}
        description="나에게 딱 맞는 구성으로"
        to="guide/age"
        position="first"
        handleBackgroundImgUrlChange={() =>
          setBackgroundImgUrl(trims[0].backgroundImgUrl.web)
        }
      >
        <MakingModeButton.GuideModeDetailList />
      </MakingModeButton>
      {trims.map((trim, index) => {
        const to = trim.name === 'Le Blanc (르블랑)' ? 'making/self/1' : '';
        const position = index === trims.length - 1 ? 'last' : 'middle';

        return (
          <MakingModeButton
            name={removeBracket(trim.name)}
            hashTag={`#${trim.hashTag}`}
            price={trim.price}
            description={trim.description}
            to={to}
            key={`making-mode-button-${index}`}
            position={position}
            handleBackgroundImgUrlChange={() =>
              setBackgroundImgUrl(trim.backgroundImgUrl.web)
            }
          >
            <MakingModeButton.MainOptionList mainOptions={trim.mainOptions} />
          </MakingModeButton>
        );
      })}
    </div>
  );
}

export default TrimCardsBox;
