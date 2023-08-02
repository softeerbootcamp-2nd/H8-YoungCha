---
to: src/components/<%= name %>/index.stories.tsx
---
import type { Meta, StoryObj } from '@storybook/react';
import <%= name %> from './index';


const meta: Meta<typeof <%= name %>> = {
  component: <%= name %>,
};

export default meta;

type Story = StoryObj<typeof <%= name %>>;

export const FirstStory: Story = {
  args: {

  },
};