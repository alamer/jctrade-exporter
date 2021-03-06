SELECT a.BARCODE
     , b.name
     , FIELD1 /*Марка*/
     , FIELD2 /*модель*/
     , FIELD3 /*Кузов*/
     , FIELD4 /*Номер?*/
     , FIELD5 /*Двигатель*/
     , FIELD7 /*Лево/право*/
     , FIELD6 /*Перед-зад*/
     , FIELD8 /*Верх-низ*/
     , FIELD13 /*Дата выпуска*/
     , case when p1.photo_num is not null then concat("https://titancars38.ru/cat_sync/{rnd}/",p1.BARCODE , '_' , p1.PHOTO_NUM , '.jpg') else '' end photo_1
     , case when p1.photo_num is not null then concat("https://titancars38.ru/cat_sync/{rnd}/",p2.BARCODE , '_' , p2.PHOTO_NUM , '.jpg') else '' end photo_2
     , case when p1.photo_num is not null then concat("https://titancars38.ru/cat_sync/{rnd}/",p3.BARCODE , '_' , p3.PHOTO_NUM , '.jpg') else '' end photo_3
     , case when p1.photo_num is not null then concat("https://titancars38.ru/cat_sync/{rnd}/",p4.BARCODE , '_' , p4.PHOTO_NUM , '.jpg') else '' end photo_4
     , FLOOR(PRICE_REC_RUB) as PRICE_REC_RUB
     , REMARK /*Примечание*/
     ,FIELD13 /*Год*/
     ,FIELD12 /*Цвет*/
     ,case when length(FIELD11)>3 then FIELD11 else '-' end oem_code /*OEM CODE*/
     ,case when field2 in ('RANGER TRUCK',
'ELF',
'TITAN',
'CANTER',
'ATLAS',
'CONDOR',
'DIESEL',
'DYNA'
) then 'export_titan5.xls' else case when name in ('бампер',
'гидроусилитель',
'дверь',
'дверь задняя',
'зеркало',
'кабина',
'капот',
'крыло',
'крыша',
'крышка багажника',
'порог',
'привод',
'рулевая рейка',
'стартер',
'стойка подвески',
'стоп-сигнал',
'ступица',
'суппорт',
'трамблер',
'фара'
) then 'export_titan2.xls' when name in ('airbag на руль',
'airbag пассажирский',
'амортизатор',
'балка',
'балка под двс',
'балка продольная',
'блок предохранителей под капот',
'блок управления efi',
'блок управления акпп',
'блок управления климат-контролем',
'блок цилиндров',
'брызговик',
'ветровики комплект',
'вилка сцепления',
'вискомуфта',
'выхлопной тракт',
'генератор',
'главный тормозной цилиндр',
'главный цилиндр сцепления',
'глушитель',
'головка блока цилиндров',
'горловина топливного бака',
'горный тормоз',
'гофра воздушного фильтра',
'гофра впускного коллектора',
'гофра выпускного колектора.',
'датчик расхода воздуха',
'диск сцепления',
'домкрат',
'жабо',
'замок зажигания',
'зеркало на крыло',
'зеркало с пятой двери',
'интеркуллер',
'карданный вал',
'катушка зажигания',
'коллектор впускной',
'колодки ручного тормоза',
'компрессор кондиционера',
'корзина сц.+диск',
'корзина сцепления',
'корпус воздушного фильтра',
'крепление капота',
'крепление крыла',
'крепление крыла и бампера',
'лонжерон',
'люк',
'лямбда-зонд',
'магнитофон',
'маховик',
'молдинг лобового стекла',
'монитор',
'мост',
'мотор печки',
'навигация',
'накладка замка багажника',
'накладка на бампер',
'накладка на ДВС',
'накладка на крыло',
'накладка на порог',
'петли 5-й двери',
'петля дверная',
'петля капота',
'петля крышки багажника',
'поворотник',
'повторитель',
'поддон',
'подкрылок',
'подушка двигателя',
'подушка кпп',
'подушка редуктора',
'поршень',
'пружина',
'рабочий цилиндр сцепления',
'радиатор кондиционера',
'радиатор основной',
'радиатор печки',
'редуктор',
'рейлинги',
'ресничка',
'рессоры',
'решетка',
'решетка на фары',
'решетка радиатора',
'рулевая колонка',
'рулевая трапеция',
'рулевая тяга',
'рулевое колесо',
'рулевой карданчик',
'рычаг',
'салон в сборе',
'сидения комплект',
'стекло заднее',
'стекло лобовое',
'стекло собачника',
'стоп-вставка',
'стоп-вставка на 5 дверь',
'табло',
'телевизор',
'телевизор салона',
'топливный насос',
'тормозной барабан',
'тормозной диск',
'торпеда',
'торсион',
'трамблер',
'туманка',
'турбина',
'тяга подвески',
'тяга поперечная',
'тяга продольная',
'тяга реактивная',
'угловой редуктор',
'фильтр топливный',
'цапфа',
'часы',
'шлейф-лента air bag',
'штаны',
'шторка багажника') then 'export_titan3.xls' when name in ('nose cut',
'АКПП',
'МКПП',
'двигатель') then 'export_titan4.xls' else 'export_titan1.xls' end end price_num
FROM
  autoshop.barcode a
  left join category b on a.CATEGORY=b.CATEGORY
  left join autoshop.photo p1 on a.barcode=p1.barcode and p1.photo_num=1
  left join autoshop.photo p2 on a.barcode=p2.barcode and p2.photo_num=2
  left join autoshop.photo p3 on a.barcode=p3.barcode and p3.photo_num=3
  left join autoshop.photo p4 on a.barcode=p4.barcode and p4.photo_num=4
WHERE
  is_enable='1'
  order by 2,3,4,5