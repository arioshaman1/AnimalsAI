module.exports = {
    root: true,
    env: {
      node: true,
    },
    extends: [
      'plugin:vue/essential', // Основные правила для Vue
      'eslint:recommended', // Рекомендуемые правила ESLint
    ],
    parserOptions: {
      parser: '@babel/eslint-parser', // Используем @babel/eslint-parser
      sourceType: 'module', // Указываем, что используем модули ES
      requireConfigFile: false, // Не требуется отдельный конфиг Babel
    },
    rules: {
      'vue/multi-word-component-names': 'off', // Отключаем правило
    },
  };