import MakingModeButton from '@/components/MakingModeButton';
import removeBracket from '../../utils/removeBracket';
import { GuideType, TrimType } from '@/types';

interface TrimCardsBoxProps {
  trims: TrimType[];
  guide: GuideType;
}

function TrimCardsBox({ trims, guide }: TrimCardsBoxProps) {
  return (
    <div className="flex justify-between gap-16px">
      <MakingModeButton
        name="Guide Mode"
        hashTag={guide.hashTag}
        price={guide.price}
        description="나에게 딱 맞는 구성으로"
        to="guide/age"
      >
        <MakingModeButton.GuideModeDetailList />
      </MakingModeButton>
      {trims.map(
        ({ name, hashTag, price, description, mainOptions }, index) => {
          return (
            <MakingModeButton
              name={removeBracket(name)}
              hashTag={`#${hashTag}`}
              price={price}
              description={description}
              to="making/self/1"
              key={`making-mode-button-${index}`}
            >
              <MakingModeButton.MainOptionList mainOptions={mainOptions} />
            </MakingModeButton>
          );
        }
      )}
    </div>
  );
}

export default TrimCardsBox;
