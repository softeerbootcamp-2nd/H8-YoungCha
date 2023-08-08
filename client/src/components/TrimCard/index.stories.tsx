import type { Meta, StoryObj } from '@storybook/react';
import TrimCard from './index';

const meta: Meta<typeof TrimCard> = {
  component: TrimCard,
};

export default meta;

type Story = StoryObj<typeof TrimCard>;

export const FirstStory: Story = {
  args: {},
};
