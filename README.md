Script responsável por criar a database/tabela de pessoa.





Banco de dados: `pessoa`


```
CREATE DATABASE pessoa;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
```

-- --------------------------------------------------------


Estrutura para tabela `pessoa`


```
CREATE TABLE `pessoa` (
  `id` int(11) NOT NULL,
  `pes_cpf` varchar(255) DEFAULT NULL,
  `pes_dt_cadastro` datetime(6) DEFAULT NULL,
  `pes_dt_ult_modificacao` datetime(6) DEFAULT NULL,
  `pes_email` varchar(255) DEFAULT NULL,
  `pes_flg_atv` int(11) NOT NULL,
  `pes_nome` varchar(255) DEFAULT NULL,
  `pes_telefone` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```


Inserindo dados para a tabela `pessoa`


```
INSERT INTO `pessoa` (`id`, `pes_cpf`, `pes_dt_cadastro`, `pes_dt_ult_modificacao`, `pes_email`, `pes_flg_atv`, `pes_nome`, `pes_telefone`, `status`) VALUES
(1, '32131232', '2024-03-24 12:44:49.000000', '2024-03-24 21:56:40.000000', 'ruuh.liima@gmail.com', 0, 'MARCOS KUDO', '+5511949083516', 1),
(2, '2', '2024-03-24 12:45:04.000000', '2024-03-24 13:04:36.000000', 'marcos.kudo@legalmanager.com', 0, 'Marcos Kudo', '1111111111111', 1),
(3, '5', '2024-03-24 13:03:46.000000', '2024-03-24 13:03:58.000000', '324343242', 0, 'Marcos Kudo', '1234565465467', 1),
(4, '3213213', '2024-03-24 21:56:27.000000', '2024-03-24 21:56:27.000000', 'ruuh.liima@gmail.com', 0, 'Rubens De Lima Sousa', '+5511949083516', 1);
```


Índices de tabela `pessoa`


```
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKfqbabfu182ryg36e53g0pq8g6` (`pes_cpf`),
  ADD KEY `UKocylhib9rg64809e1o9li94so` (`pes_nome`) USING BTREE;
```



AUTO_INCREMENT de tabela `pessoa`


```
ALTER TABLE `pessoa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;
```
