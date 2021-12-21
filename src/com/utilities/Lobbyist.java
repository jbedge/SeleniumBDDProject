package com.utilities;

import sun.plugin.javascript.navig.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Lobbyist implements Comparable {
	private String first = "";
	private String last = "";
	private String middle = "";
	private String fullName = "";
	private String email = "";
	private String note = "";
	private String street1 = "";
	private String street2 = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	private String phone = "";
	private String fax = "";
	private String key = "";
	private String stateList = "";
	private ArrayList<String> phones = new ArrayList();
	private ArrayList<String> clients = new ArrayList();
	private String stateAbbrev = "";
	private String firmName = "";
	private ArrayList<String> tags = new ArrayList();
	private ArrayList<String> topics = new ArrayList();
	private String id = "";
	private int numClients = -1;
	private String imagePath = "";
	private String bio = "";
	private String numYears = "";
	private String approach = "";
	private String relationships = "";
	private String successes = "";
	private String type = "";
	private Date updateDate = null;
	private ArrayList<String> recommendations = new ArrayList();
	private String searchText = "";
	private ArrayList<Link> links = new ArrayList();
	private ArrayList<String> states = new ArrayList();
	private String org = "";
	private String address1 = "";
	private String address2 = "";
	private String clientName = "";

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first.trim().replace("&#39;", "'");
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last.trim().replace("&#39;", "'");
	}

	public String getEmail() {
		return email.toLowerCase().trim();
	}

	public String getEmailDomain() {
		String temp = email.toLowerCase();
		if (temp.contains("@"))
			temp = temp.substring(temp.indexOf("@") + 1);
		return temp;
	}

	public void setEmail(String email) {

		this.email = email.toLowerCase().replace("\n", "");
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStreet1() {
		return street1.replace(";", ",");
	}

	public String getSimpleStreet1() {
		return street1.replace(";", ",").replace(",", "");
	}

	public void setStreet1(String street1) {
		this.street1 = street1.trim();
	}

	public String getStreet2() {
		return street2.replace(";", ",");
	}

	public void setStreet2(String street2) {
		this.street2 = street2.trim();
	}

	public String getCity() {

		return city;
	}

	public void setCity(String city) {
		this.city = city.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state.trim();
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public ArrayList<String> getPhones() {
		return phones;
	}

	public void setPhones(ArrayList<String> phones) {
		this.phones = phones;
	}

	public ArrayList<String> getClients() {
		Collections.sort(clients);
		return clients;
	}

	public ArrayList<String> getClients(boolean toUpperCase) {
		Collections.sort(clients);

		ArrayList<String> temp = new ArrayList();
		for (String client : clients) {
			client = client.replace("RED RIVER", "Red River (Technology)");
			if (toUpperCase) {
				temp.add(client.toUpperCase().replace(".", "").replace(",", "").trim());
			} else
				temp.add(client.trim());
		}
		return temp;
	}

	public void setClients(ArrayList<String> clients) {
		if (clients == null)
			return;
		this.clients = clients;
		numClients = clients.size();
	}

	public void addClient(String client) {

		client = client.replace(";", ",");
		client = client.replace("&amp;", "&");
		client = client.replace("&#39;", "'");
		client = client.replace("=E2=80=99", "'");
		client = client.replace("’", "'");

		client = client.trim();
		if (clients.contains(client) == false && client.length() > 0)
			clients.add(client);
		numClients = clients.size();
	}

	public void addPhone(String phone) {
		phones.add(phone);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		phone = phone.replace("x_____", "").trim();
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStateList() {
		return stateList;
	}

	public void setStateList(String stateList) {
		this.stateList = stateList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((stateAbbrev == null) ? 0 : stateAbbrev.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lobbyist other = (Lobbyist) obj;

		// if (Driver.testMode)
		// {
		// System.out.println("compare:");
		// System.out.println("this:"+this);
		// System.out.println("other:"+other);
		// }
		// if (this.getStateList().equals("Maine") &&
		// other.getStateList().equals("Maine")
		// && this.getEmail().equalsIgnoreCase(other.getEmail()))
		// return true;
		if (this.getStateList().equals("Virginia") && other.getStateList().equals("Virginia")
				&& this.getFirst().equalsIgnoreCase(other.getFirst())
				&& this.getLast().equalsIgnoreCase(other.getLast()) && this.getLast().contains("Keeney") == false
				&& other.getLast().contains("Keeney") == false)
			return true;

		if (this.getStateList().equals("California") && other.getStateList().equals("California")
				&& this.getFirst().equalsIgnoreCase(other.getFirst())
				&& this.getLast().equalsIgnoreCase(other.getLast())
				&& this.getLast().equalsIgnoreCase("ROBINSON") == false
				&& other.getLast().equalsIgnoreCase("ROBINSON") == false)
			return true;

		if (this.getStateAbbrev().equals("MA") && other.getStateAbbrev().equals("MA")) {
			if ((this.getFirst() + "" + this.getLast() + this.getStreet1())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getStreet1()))
				return true;

		}

		if (this.getStateAbbrev().equals("MA") && other.getStateAbbrev().equals("MA")) {
			if ((this.getFirst() + "" + this.getLast() + this.getPlainPhone())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getPlainPhone()))
				return true;

		}

		if (this.getStateAbbrev().equals("CA") && other.getStateAbbrev().equals("CA")) {
			if (this.getType().equals("PREMIUM") || other.getType().equals("PREMIUM")) {

				if ((this.getFirst() + "" + this.getLast() + this.getStreet1())
						.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getStreet1()))
					return true;

				if ((this.getFirst() + "" + this.getLast() + this.getEmail())
						.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getEmail()))
					return true;
			}
		}
		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US")
				&& this.getFullName().contains("Ryan Thompson") && other.getFullName().contains("Ryan Thompson")) {
			return this.getFirmName().equalsIgnoreCase(other.getFirmName());
		}
		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US") && this.getEmail().length() > 0
				&& other.getEmail().length() > 0) {
			if ((this.getFirst() + "" + this.getLast() + this.getEmail())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getEmail()))
				return true;

		}

		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US")) {
			if ((this.getFullName().toUpperCase().startsWith("LINDA")
					&& this.getFullName().toUpperCase().endsWith("MAYNOR"))
					&& (other.getFullName().toUpperCase().startsWith("LINDA")
							&& other.getFullName().toUpperCase().endsWith("MAYNOR")))
				return true;

		}
		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US")) {
			if ((this.getEmail().equals("dkantor@steptoe.com")) && other.getEmail().equals("dkantor@steptoe.com"))
				return true;
			if ((this.getFullName().startsWith("Doug") && this.getFullName().endsWith("Kantor"))
					&& (other.getFullName().startsWith("Doug") && other.getFullName().endsWith("Kantor")))
				return true;

		}
		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US") && this.getEmail().length() > 0
				&& other.getEmail().length() > 0) {
			if ((this.getFirst() + "" + this.getLast() + this.getEmail())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getEmail()))
				return true;

		}

		if (this.getStateAbbrev().equals("WA") && other.getStateAbbrev().equals("WA") && this.getEmail().length() > 0
				&& other.getEmail().length() > 0) {
			if ((this.getFirst() + "" + this.getLast() + this.getEmail())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getEmail()))
				return true;

		}

		if (this.getStateAbbrev().equals("WA") && other.getStateAbbrev().equals("WA") && this.getFullName().length() > 0
				&& other.getFullName().length() > 0) {
			if ((this.getFullName()).equalsIgnoreCase(other.getFullName()))
				return true;

		}

		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US") && this.getFirmName().length() > 0
				&& other.getFirmName().length() > 0) {
			if ((this.getFirst() + "" + this.getLast() + this.getFirmName())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getFirmName()))
				return true;

		}

		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US")
				&& this.getSimpleStreet1().length() > 0 && other.getSimpleStreet1().length() > 0) {
			if ((this.getFirst() + "" + this.getLast() + this.getSimpleStreet1())
					.equalsIgnoreCase(other.getFirst() + other.getLast() + other.getSimpleStreet1()))
				return true;

		}

		// if (this.getStateAbbrev().equals("US") &&
		// other.getStateAbbrev().equals("US")
		// && this.getFullName().equalsIgnoreCase(other.getFullName()) &&
		// this.getFirmName().length() > 0
		// && other.getFirmName().length() > 0
		// && this.getFirmName().equalsIgnoreCase(other.getFirmName()) == false)
		// {
		// return false;
		//
		// }

		if (this.getStateAbbrev().equals("US") && other.getStateAbbrev().equals("US")
				&& (this.getFullName().equals("Ryan Thompson") && other.getFullName().equals("Ryan Thompson"))
				&& this.getFullName().equalsIgnoreCase(other.getFullName()) && this.getFirmName().length() > 0
				&& other.getFirmName().length() > 0
				&& this.getFirmName().equalsIgnoreCase(other.getFirmName()) == false) {
			return false;

		}

		// if (this.getStateList().equals("Vermont") &&
		// other.getStateList().equals("Vermont")) {
		//
		// if (this.getFullName().equalsIgnoreCase(other.getFullName()))
		// return this.getEmailDomain().equals(other.getEmailDomain());
		// }

		if (this.getOrg().length() > 0 || other.getOrg().length() > 0) {
			if (this.getOrg().length() > 0 && other.getOrg().length() > 0
					&& this.getStateAbbrev().equals(other.getStateAbbrev()))
				return (this.getOrg().equals(other.getOrg()) && this.getEmail().equals(other.getEmail()));
			else
				return false;
		}
		if (this.getStateAbbrev().equals("MN") && other.getStateAbbrev().equals("MN")) {
			if (this.getFullName().replace("\"", "").equalsIgnoreCase(other.getFullName().replace("\"", ""))
					&& this.getStateAbbrev().equals(other.getStateAbbrev()) && this.getStreet1().length() > 0
					&& other.getStreet1().length() > 0 && this.getStreet1().equalsIgnoreCase(other.getStreet1()))
				return true;

		}
		if (this.getStateAbbrev().equals("MN") && other.getStateAbbrev().equals("MN")) {
			if (this.getFullName().replace("\"", "").equalsIgnoreCase(other.getFullName().replace("\"", ""))
					&& this.getStateAbbrev().equals(other.getStateAbbrev())
					&& this.getEmail().equals(other.getEmail()) == false)
				return false;

		}

		if (this.getStateAbbrev().equals("UT") && other.getStateAbbrev().equals("UT")) {
			return this.getFullName().replace("\"", "").equalsIgnoreCase(other.getFullName().replace("\"", ""))
					&& this.getStateAbbrev().equals(other.getStateAbbrev()) && this.getPhone().equals(other.getPhone());
		}
		if (this.getFullName().replace("\"", "").equalsIgnoreCase(other.getFullName().replace("\"", ""))
				&& this.getStateAbbrev().equals(other.getStateAbbrev()))
			return true;

		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equalsIgnoreCase(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equalsIgnoreCase(other.last))
			return false;

		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (this.getStateAbbrev().equals("MA") && other.getStateAbbrev().equals("MA")
				&& !fullName.replace(".", "").equalsIgnoreCase(other.fullName.replace(".", "")))
			return false;
		else if (!fullName.equalsIgnoreCase(other.fullName))
			return false;

		if (true || this.getStates().size() <= 1 && other.getStates().size() <= 1) {
			if (stateAbbrev == null) {
				if (other.stateAbbrev != null)
					return false;
			} else if (!stateAbbrev.equals(other.stateAbbrev))
				return false;
		} else {
			System.out.println("check for multiple state match:" + this.getFullName() + "|" + this.getStates() + "\t"
					+ other.getFullName() + "|" + other.getStates());
			boolean hasMatch = false;
			for (String otherState : other.getStates()) {
				if (this.getStates().contains(otherState)) {
					hasMatch = true;
					break;
				}
			}
			for (String thisState : this.getStates()) {
				if (other.getStates().contains(thisState)) {
					hasMatch = true;
					break;
				}
			}
			System.out.println("has match:" + hasMatch);
			if (hasMatch == false)
				return false;
		}
		// if (stateAbbrev.equals("NY")) {
		// if (street1 == null) {
		// if (other.street1 != null)
		// return false;
		// } else if (!street1.equals(other.street1))
		// return false;
		// }
		return true;
	}

	private String getPlainPhone() {
		String clean = "";
		for (int i = 0; i < phone.length(); i++) {
			if (Character.isDigit(phone.charAt(i)))
				clean += "" + phone.charAt(i);
		}
		return clean;

	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Lobbyist) {
			Lobbyist other = (Lobbyist) o;

			if (this.getStateAbbrev().equals(other.stateAbbrev)) {

				String thisName = last + "," + first;
				String otherName = other.last + "," + other.first;
				return thisName.toUpperCase().compareTo(otherName.toUpperCase());
			} else
				return this.getStateAbbrev().compareTo(other.getStateAbbrev());
		} else
			return this.hashCode() - o.hashCode();

	}

//	@Override
//	public String toString() {
//		if (false)
//			return this.getFullName();
//		else
//			return "Lobbyist [first=" + first + ", last=" + last + ", middle=" + middle + ", fullName=" + fullName
//					+ ", email=" + email + ", note=" + note + ", street1=" + street1 + ", street2=" + street2
//					+ ", city=" + city + ", state=" + state + ", zip=" + zip + ", phone=" + phone + ", fax=" + fax
//					+ ", key=" + key + ", stateList=" + stateList + ", phones=" + phones + ", clients=" + clients
//					+ ", stateAbbrev=" + stateAbbrev + ", firmName=" + firmName + ", tags=" + tags + ", topics="
//					+ topics + ", id=" + id + ", numClients=" + numClients + ", imagePath=" + imagePath + ", bio=" + bio
//					+ ", numYears=" + numYears + ", approach=" + approach + ", relationships=" + relationships
//					+ ", successes=" + successes + ", type=" + type + ", updateDate=" + updateDate
//					+ ", recommendations=" + recommendations + ", searchText=" + searchText + ", links=" + links
//					+ ", states=" + states + ", org=" + org + "]";
//	}

	@Override
	public String toString() {
		if (false)
			return this.getFullName();
		else
			return "values = [fullName=" + fullName + ", clientName=" + clientName + ", address1=" + address1 + ", address2=" + address2
					+ ", city=" + city + ", state=" +state+ ", zip="+zip+ ", email="+email+"]";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		// while (fullName.contains(" "))
		// fullName = fullName.replace(" ", " ");
		this.fullName = fullName.replace("  ", " ").replace("&#39;", "'");
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName.trim().replace("K&L GATES LLP", "K&L Gates");
		if (this.firmName.toUpperCase().contains("Cornerstone Government".toUpperCase()))
			this.firmName = "Cornerstone Government Affairs";
		if (this.firmName.toUpperCase().contains("McGuireWoods".toUpperCase()))
			this.firmName = "McGuireWoods Consulting";

	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public ArrayList<String> getTopics() {
		return topics;
	}

	public void setTopics(ArrayList<String> topics) {
		this.topics = topics;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumClients() {
		return numClients;
	}

	public void setNumClients(int numClients) {
		this.numClients = numClients;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getNumYears() {
		return numYears;
	}

	public void setNumYears(String numYears) {
		this.numYears = numYears;
	}

	public String getApproach() {
		return approach;
	}

	public void setApproach(String approach) {
		this.approach = approach;
	}

	public String getRelationships() {
		return relationships;
	}

	public void setRelationships(String relationships) {
		this.relationships = relationships;
	}

	public String getSuccesses() {
		return successes;
	}

	public void setSuccesses(String successes) {
		this.successes = successes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

//	public ArrayList<Recommendation> getRecommendations() {
//		return recommendations;
//	}
//
//	public void setRecommendations(ArrayList<Recommendation> recommendations) {
//		this.recommendations = recommendations;
//	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

	public ArrayList<String> getStates() {
		if (states.size() == 0 && stateAbbrev.length() > 0) {
			states.add(stateAbbrev);
		}

		return states;
	}

	public void setStates(ArrayList<String> states) {
		this.states = states;
	}

	public String toFormattedString() {
		String text = "";

		text += first + " " + last + " (" + stateAbbrev + ")\n";
		// text += getProfileURL();
		if (firmName.length() > 0)
			text += firmName + "\n";
		if (email.length() > 0)
			text += email + "\n";
		if (phone.length() > 0)
			text += phone + "\n";
		text += street1 + ", " + (street2.length() > 0 ? " " + street2 + ", " : "") + city + ", " + state + " " + zip
				+ "\n";
//		text += DBInterface.makeListIntoString(clients);
		return text;
	}

	public void removeClient(String name) {
		clients.remove(name);

	}

	public void addClients(ArrayList<String> list) {
		for (String item : list) {
			item = item.replace("=E2=80=99", "'");
			item = item.replace("’", "'");
			if (clients.contains(item) == false && item.trim().length() > 0)
				clients.add(item);
		}
		numClients = clients.size();
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org.replace("&amp;", "&").replace("\n", "");
	}

	public String getProfileURL() {
		return "https://lobbylinx.com/profile.php?profileid=" + id;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
