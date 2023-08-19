import type { Meta, StoryObj } from '@storybook/react';
import FeedbackCard from './index';

const meta: Meta<typeof FeedbackCard> = {
  component: FeedbackCard,
};

export default meta;

type Story = StoryObj<typeof FeedbackCard>;

export const FirstStory: Story = {
  args: {},
};
