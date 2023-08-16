import type { Meta, StoryObj } from '@storybook/react';
import PopUp from './index';
import {
  ChangeToGasolinePopUp,
  ModelChangePopUp,
  ModeChangePopUp,
  SelfModeCard,
  GuideModeCard,
} from './constant';

const meta: Meta<typeof PopUp> = {
  component: PopUp,
};

export default meta;

type Story = StoryObj<typeof PopUp>;

export const ModelChange: Story = {
  args: {
    children: (
      <div>
        <PopUp.PopUpMain
          title={ModelChangePopUp.title}
          imgSrc={<ModelChangePopUp.ImgSrc />}
        />
        <PopUp.Description
          firstLine={ModelChangePopUp.firstLine}
          secondLine={ModelChangePopUp.secondLine}
        />
        <PopUp.PopUpButton
          greyButtonContent={ModelChangePopUp.greyButtonContent}
          blueButtonContent={ModelChangePopUp.blueButtonContent}
        />
      </div>
    ),
  },
};

export const PowerTrainChange: Story = {
  args: {
    children: (
      <div>
        <PopUp.PopUpMain
          title={ChangeToGasolinePopUp.title}
          imgSrc={<ChangeToGasolinePopUp.ImgSrc />}
        />
        <PopUp.SingleDescription
          singleLine={ChangeToGasolinePopUp.singleLine}
        />
        <PopUp.DualMufflerImg />
        <PopUp.PopUpButton
          greyButtonContent={ChangeToGasolinePopUp.greyButtonContent}
          blueButtonContent={ChangeToGasolinePopUp.blueButtonContent}
        />
      </div>
    ),
  },
};

export const ModeChangeToGuide: Story = {
  args: {
    children: (
      <div>
        <PopUp.PopUpMain
          title={ModeChangePopUp.title}
          imgSrc={<ModeChangePopUp.ImgSrc />}
        />
        <PopUp.ModeSelectCard
          currentMode="self"
          mode={SelfModeCard.mode}
          firstLine={SelfModeCard.firstLine}
          secondLine={SelfModeCard.secondLine}
          thirdLine={SelfModeCard.thirdLine}
        />
        <PopUp.ModeSelectCard
          currentMode="self"
          mode={GuideModeCard.mode}
          firstLine={GuideModeCard.firstLine}
          secondLine={GuideModeCard.secondLine}
          thirdLine={GuideModeCard.thirdLine}
        />
      </div>
    ),
  },
};

export const ModeChangeToSelf: Story = {
  args: {
    children: (
      <div>
        <PopUp.PopUpMain
          title={ModeChangePopUp.title}
          imgSrc={<ModeChangePopUp.ImgSrc />}
        />
        <PopUp.ModeSelectCard
          currentMode="guide"
          mode={SelfModeCard.mode}
          firstLine={SelfModeCard.firstLine}
          secondLine={SelfModeCard.secondLine}
          thirdLine={SelfModeCard.thirdLine}
        />
        <PopUp.ModeSelectCard
          currentMode="guide"
          mode={GuideModeCard.mode}
          firstLine={GuideModeCard.firstLine}
          secondLine={GuideModeCard.secondLine}
          thirdLine={GuideModeCard.thirdLine}
        />
      </div>
    ),
  },
};
