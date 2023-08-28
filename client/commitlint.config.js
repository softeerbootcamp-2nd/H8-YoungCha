export default {
  extends: ['@commitlint/config-conventional'],
  rules: {
    'type-enum': [
      2,
      'always',
      ['feat', 'fix', 'docs', 'design', 'refactor', 'test', 'chore', 'merge'],
    ],
    'body-case': [0, 'never'],
    'header-case': [0, 'never'],
    'subject-case': [0, 'never'],
  },
};
