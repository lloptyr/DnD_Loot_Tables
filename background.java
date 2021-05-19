package Loot_Table_project;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class background extends JPanel implements ActionListener
{
	Random rand = new Random();
	int CP = 0, SP = 0, GP = 0, EP = 0, PP = 0;
	int gemamount = 0, artamount = 0;
	int twentyfivegpart = 0, twofiftygpart = 0, sevenfiftygpart = 0, twentyfivehundredgpart = 0, seventyfivehundredgpart = 0;
	int dice, d100;
	String DELIMITER = ",";
	String MagicItems = "", Art = "", Gems = "";
	String gemtype = "", arttype = "";
	JLabel text;
	JComboBox<String> cr;

	public background()
	{

		setLayout(new BorderLayout());

		String[] crstring =
		{ "0-4", "5-10", "11-16", "17+" };
		cr = new JComboBox<String>(crstring);
		cr.setSelectedIndex(0);
		// cr.addActionListener(this);
		cr.setActionCommand("CR");
		add(cr, BorderLayout.NORTH);

		JButton button = new JButton();
		// button.setBounds(400,100,150,50);
		button.setText("Individual");
		button.addActionListener(this);
		add(button, BorderLayout.EAST);

		JButton button1 = new JButton();
		// button1.setBounds(50,100,150,50);
		button1.setText("Hoard");
		button1.addActionListener(this);
		add(button1, BorderLayout.WEST);

		text = new JLabel();
		text.setText(String.format("<html>%d CP, %d SP, %d EP, %d GP, %d PP <br/>Gems: %s <br/>Art: %s <br/>Magic Items: %s</html>", CP, SP, EP, GP, PP, Gems, Art, MagicItems));
		// text.setBounds(200, 300, 250, 40);
		JPanel textpanel = new JPanel();
		add(textpanel, BorderLayout.CENTER);
		textpanel.add(text, BorderLayout.CENTER);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		text.setText(String.format("<html>%d CP, %d SP, %d EP, %d GP, %d PP <br/>Gems: %s <br/>Art: %s <br/>Magic Items: %s</html>", CP, SP, EP, GP, PP, Gems, Art, MagicItems));
	}

	public void indiv()
	{

		try (BufferedReader br = Files.newBufferedReader(Paths.get("datasheets/IndividualEncounters.csv")))
		{
			String DELIMITER = ",";
			String line;
			Art = "";
			Gems = "";
			MagicItems = "";
			while ((line = br.readLine()) != null)
			{
				// System.out.println(line);
				String[] columns = line.split(DELIMITER);
				if (columns[0].equals("CR: " + cr.getSelectedItem()))
				{
					int d100 = rand.nextInt(100) + 1;
					while ((line = br.readLine()) != null)
					{
						columns = line.split(DELIMITER);
						if (Integer.parseInt(columns[0]) <= d100 && Integer.parseInt(columns[1]) >= d100)
						{
							int CPmin = Integer.parseInt(columns[2]);
							int CPmax = Integer.parseInt(columns[3]);
							int SPmin = Integer.parseInt(columns[4]);
							int SPmax = Integer.parseInt(columns[5]);
							int EPmin = Integer.parseInt(columns[6]);
							int EPmax = Integer.parseInt(columns[7]);
							int GPmin = Integer.parseInt(columns[8]);
							int GPmax = Integer.parseInt(columns[9]);
							int PPmin = Integer.parseInt(columns[10]);
							int PPmax = Integer.parseInt(columns[11]);
							CP = rand.nextInt(CPmax + 1 - CPmin) + CPmin;
							SP = rand.nextInt(SPmax + 1 - SPmin) + SPmin;
							EP = rand.nextInt(EPmax + 1 - EPmin) + EPmin;
							GP = rand.nextInt(GPmax + 1 - GPmin) + GPmin;
							PP = rand.nextInt(PPmax + 1 - PPmin) + PPmin;
							repaint();
							break;

						}

					}
					break;
				}
			}
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	public void hord()
	{
		try (BufferedReader br = Files.newBufferedReader(Paths.get("datasheets/TreasureHoard.csv")))
		{

			String line;
			while ((line = br.readLine()) != null)
			{
				String[] columns = line.split(DELIMITER);
				if (columns[0].equals("CR: " + cr.getSelectedItem()))
				{
					line = br.readLine();
					columns = line.split(DELIMITER);
					int CPmin = Integer.parseInt(columns[2]);
					int CPmax = Integer.parseInt(columns[3]);
					int SPmin = Integer.parseInt(columns[5]);
					int SPmax = Integer.parseInt(columns[6]);
					int EPmin = Integer.parseInt(columns[7]);
					int EPmax = Integer.parseInt(columns[8]);
					int GPmin = Integer.parseInt(columns[10]);
					int GPmax = Integer.parseInt(columns[11]);
					int PPmin = Integer.parseInt(columns[12]);
					int PPmax = Integer.parseInt(columns[13]);
					CP = rand.nextInt(CPmax + 1 - CPmin) + CPmin;
					SP = rand.nextInt(SPmax + 1 - SPmin) + SPmin;
					EP = rand.nextInt(EPmax + 1 - EPmin) + EPmin;
					GP = rand.nextInt(GPmax + 1 - GPmin) + GPmin;
					PP = rand.nextInt(PPmax + 1 - PPmin) + PPmin;
					d100 =rand.nextInt(100) + 1;
					while ((line = br.readLine()) != null)
					{
						columns = line.split(DELIMITER);

						if (Integer.parseInt(columns[0]) <= d100 && Integer.parseInt(columns[1]) >= d100)
						{
							Gems = "";
							Art = "";
							MagicItems = "";
							columns = line.split(DELIMITER);

							gemreader(columns, "datasheets/Gems.csv", "tengpgem");
							gemreader(columns, "datasheets/Gems.csv", "fiftygpgem");
							gemreader(columns, "datasheets/Gems.csv", "fivehundredgpgem");
							gemreader(columns, "datasheets/Gems.csv", "fivethousandgpgem");
							gemreader(columns, "datasheets/Gems.csv", "hundredgpgem");
							gemreader(columns, "datasheets/Gems.csv", "thousandgpgem");
							artreader(columns, "datasheets/Art.csv", "twentyfivegpart");
							artreader(columns, "datasheets/Art.csv", "sevenfiftygpart");
							artreader(columns, "datasheets/Art.csv", "seventyfivehundredgpart");
							artreader(columns, "datasheets/Art.csv", "twentyfivehundredgpart");
							artreader(columns, "datasheets/Art.csv", "twofiftygpart");
							String magicitemtableone = columns[9];
							switch (magicitemtableone)
							{
							case "Magic Item Table A":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableA.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}

								break;
							case "Magic Item Table B":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableB.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table C":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableC.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table D":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableD.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table E":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableE.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table F":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableF.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table G":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableG.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													if (newcolumns[2].equals("SubTableG"))
													{
														try (BufferedReader brs = Files.newBufferedReader(Paths.get("datasheets/SubtableG.csv")))
														{
															String subline = brs.readLine();
															String[] subcolumns = subline.split(DELIMITER);
															while ((subline = brs.readLine()) != null)
															{
																subline = brs.readLine();
																subcolumns = subline.split(DELIMITER);

																int d8 = rand.nextInt(8) + 1;
																while (true)
																{
																	if (Integer.parseInt(subcolumns[0]) == d8)
																	{
																		MagicItems += "<br/Figurine of Wonderous Power: " + subcolumns[1];
																		break;
																	} else
																	{
																		subline = brs.readLine();
																		subcolumns = subline.split(DELIMITER);
																	}
																}
																break;
															}
															brs.close();
														} catch (IOException ex)
														{
															ex.printStackTrace();
														}
													} else
													{
														MagicItems += "<br/>" + newcolumns[2];
														d100 = rand.nextInt(100) + 1;
														break;
													}
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);

												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table H":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableH.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table I":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableI.csv")))
								{
									d100 =rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													if (newcolumns[2].equals("SubTableI"))
													{
														try (BufferedReader brs = Files.newBufferedReader(Paths.get("datasheets/SubtableI.csv")))
														{
															String subline = brs.readLine();
															String[] subcolumns = subline.split(DELIMITER);
															while ((subline = brs.readLine()) != null)
															{
																
																subcolumns = subline.split(DELIMITER);

																int d12 = rand.nextInt(12) + 1;
																while (true)
																{
																	if (Integer.parseInt(subcolumns[0]) <= d12 && Integer.parseInt(subcolumns[1]) >= d12)
																	{
																		MagicItems += "<br/>Magic " + subcolumns[2];
																		break;
																	} else
																	{
																		subline = brs.readLine();
																		subcolumns = subline.split(DELIMITER);
																	}
																}
																break;
															}
															brs.close();
														} catch (IOException ex)
														{
															ex.printStackTrace();
														}
													} else
													{
														MagicItems += "<br/>" + newcolumns[2];
														d100 = rand.nextInt(100) + 1;
														break;
													}
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
//												System.out.println(d100);
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table A/B":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableA.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[6], columns[5]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableB.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							case "Magic Item Table F/G":
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableF.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[6], columns[5]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								try (BufferedReader bri = Files.newBufferedReader(Paths.get("datasheets/MagicTableG.csv")))
								{
									d100 = rand.nextInt(100) + 1;
									int itemamount = 0;
									String newline = bri.readLine();
									String[] newcolumns = newline.split(DELIMITER);
									columnrandom dicerollcolumn = new columnrandom();
									itemamount = dicerollcolumn.columnrand(columns[8], columns[7]);
									bri.mark(300);
									while ((newline = bri.readLine()) != null)
									{

										newcolumns = newline.split(DELIMITER);

										for (int i = 0; i < itemamount; i++)
										{
											while (true)
											{

												if (Integer.parseInt(newcolumns[0]) <= d100 && Integer.parseInt(newcolumns[1]) >= d100)
												{
													MagicItems += "<br/>" + newcolumns[2];
													d100 = rand.nextInt(100) + 1;
													break;
												} else
												{
													newline = bri.readLine();
													newcolumns = newline.split(DELIMITER);
												}
											}
											bri.reset();
											newline = bri.readLine();
											newcolumns = newline.split(DELIMITER);
										}
										break;
									}

									br.close();
								} catch (IOException ex)
								{
									ex.printStackTrace();
								}
								break;
							}
							repaint();
							break;
						}

					}
					break;
				}

			}
			br.close();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	public void gemreader(String[] columns, String path, String gemtype)
	{

		try (BufferedReader brg = Files.newBufferedReader(Paths.get(path)))
		{
			if (columns[4].equals(gemtype))
			{
				String newline = brg.readLine();
				String[] newcolumns = newline.split(DELIMITER);
				while (!newcolumns[1].equals(gemtype))
				{
					newline = brg.readLine();
					newcolumns = newline.split(DELIMITER);
				}

				String dicenum = newcolumns[0];
				int die = rolldice(dicenum);
				columnrandom diceroll = new columnrandom();
				gemamount = diceroll.columnrand(columns[3], columns[2]);

				if (newcolumns[1].equals(gemtype))
				{
					brg.mark(300);
					while ((newline = brg.readLine()) != null)
					{

						newcolumns = newline.split(DELIMITER);
						for (int i = 0; i < gemamount; i++)
						{
							while (true)
							{

								if (Integer.parseInt(newcolumns[0]) == die)
								{
									Gems += "<br/>" + newcolumns[1];
									die = rolldice(dicenum);
									break;
								} else
								{
									newline = brg.readLine();
									newcolumns = newline.split(DELIMITER);
								}
							}
							brg.reset();
							newline = brg.readLine();
							newcolumns = newline.split(DELIMITER);
						}
						break;
					}

				}

			}
			brg.close();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public void artreader(String[] columns, String path, String arttype)
	{

		try (BufferedReader bra = Files.newBufferedReader(Paths.get(path)))
		{
			if (columns[4].equals(arttype))
			{

				String newline = bra.readLine();
				String[] newcolumns = newline.split(DELIMITER);
				while (!newcolumns[1].equals(arttype))
				{
					newline = bra.readLine();
					newcolumns = newline.split(DELIMITER);
				}
				String dicenum = newcolumns[0];
				int die = rolldice(dicenum);
				columnrandom dicerollcolumn = new columnrandom();
				artamount = dicerollcolumn.columnrand(columns[3], columns[2]);

				if (newcolumns[1].equals(arttype))
				{
					bra.mark(300);
					while ((newline = bra.readLine()) != null)
					{

						newcolumns = newline.split(DELIMITER);
						for (int i = 0; i < artamount; i++)
						{
							while (true)
							{

								if (Integer.parseInt(newcolumns[0]) == die)
								{
									Art += "<br/>" + newcolumns[1];
									die = rolldice(dicenum);
									break;
								} else
								{
									newline = bra.readLine();
									newcolumns = newline.split(DELIMITER);
								}
							}
							bra.reset();
							newline = bra.readLine();
							newcolumns = newline.split(DELIMITER);
						}
						break;
					}

				}

			}
			bra.close();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public int rolldice(String dice)
	{
		int diemin = 1;
		int diemax = Integer.parseInt(dice);
		return rand.nextInt(diemax - diemin + 1) + diemin;

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Individual"))
		{
			indiv();
		}
		if (e.getActionCommand().equals("Hoard"))
		{
			hord();
		}
	}
}
