import type { Meta, StoryObj } from '@storybook/react';
import OptionCard from './index';

const meta: Meta<typeof OptionCard> = {
  component: OptionCard,
};

export default meta;

type Story = StoryObj<typeof OptionCard>;

export const FirstStory: Story = {
  args: {},
};
